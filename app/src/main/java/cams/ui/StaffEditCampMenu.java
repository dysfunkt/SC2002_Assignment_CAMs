package cams.ui;

import java.util.Date;
import java.util.Scanner;

import cams.MainApp;
import cams.object.appitem.Camp;
import cams.object.person.eFaculty;
import cams.util.ScannerHelper;

public class StaffEditCampMenu extends BaseUI{
    private Camp campToEdit;
    public StaffEditCampMenu(Camp campToEdit){
        this.campToEdit = campToEdit;
    }

    private Scanner input = ScannerHelper.getScannerInput();

    protected int generateMenuScreen() {
        printHeader("These are the options to edit: ");
        System.out.println("1) Camp Name");
        System.out.println("2) Start Date");
        System.out.println("3) End Date");
        System.out.println("4) Registration Close Date");
        System.out.println("5) Faculty of Camp");
        System.out.println("6) Camp Location");
        System.out.println("7) Total Number of Student Slots");
        System.out.println("8) Total Number of Committee Slots");
        System.out.println("9) Camp Description");
        System.out.println("10) List of Attendees");
        System.out.println("11) List of Camp Committee Members");
        System.out.println("12) List of Leavers");
        System.out.println("13) Visibility");
        printBreaks();
        int choice = doMenuChoice(10, 0);
        switch (choice) {
            case 1:
                String campName;

                // In case the previous input was a primitive data type
                input.nextLine();

                // Set camp details
                System.out.print("Enter Camp Name: ");
                campName = input.nextLine();
                campToEdit.setCampName(campName);
                return 1;
            case 2:
                Date startDate;
                startDate = ScannerHelper.getDateInput("Enter the date (yyyy-MM-DD) that the camp starts (terminate with 0): ");
                campToEdit.setStartDate(startDate);
                return 1;
            case 3:
                Date endDate;
                endDate = ScannerHelper.getDateInput("Enter the date (yyyy-MM-DD) that the camp starts (terminate with 0): ");
                campToEdit.setEndDate(endDate);
                return 1;
            case 4:
                Date regCloseDate;
                regCloseDate = ScannerHelper.getDateInput("Enter the date (yyyy-MM-DD) that the registration ends (terminate with 0): ");
                campToEdit.setRegCloseDate(regCloseDate);
                return 1;
            case 5:
                eFaculty userGroup;
                int groupChoice = ScannerHelper.getIntegerInput("Set group camp is open to (1 for your faculty, 2 for whole NTU): ", 0 ,3);
                switch (groupChoice) {
                    case 1:
                        userGroup = MainApp.currentUser.getFaculty();
                        return 1;
                    case 2:
                        userGroup = Enum.valueOf(eFaculty.class, "NTU");
                        return 1;
                    default:
                        userGroup = MainApp.currentUser.getFaculty();
                }
            case 6:
                String campLocation;
                System.out.print("Enter Camp Location: ");
                campLocation = input.nextLine();
                campToEdit.setCampLocation(campLocation);
                return 1;
            case 7:
                int campTotalSlots;
                campTotalSlots = ScannerHelper.getIntegerInput("Enter total # of slots for students: ");
                campToEdit.setCampTotalSlots(campTotalSlots);
                return 1;
            case 8:
                int campCommitteeSlots;
                campCommitteeSlots = ScannerHelper.getIntegerInput("Enter total # of slots for camp committee (Max 10): ", 0, 11);
                campToEdit.setCampCommitteeSlots(campCommitteeSlots);
                return 1;
            case 9:
                String campDescription;
                System.out.print("Enter Camp Description: ");
                campDescription = input.nextLine();
                campToEdit.setCampDescription(campDescription);
                return 1;
            case 10:
                String staffInCharge;
                staffInCharge = MainApp.currentUser.getUserID();
                campToEdit.setStaffInCharge(staffInCharge);
                return 1;
            case 11:
                Boolean visibility;
                visibility = ScannerHelper.getYesNoInput("Make camp visible?");
                campToEdit.setVisibility(visibility);
                return 1;
            case 0:
                System.out.println("Returning to Staff Menu");
                return 1; //shutdown
            default:
                throw new MenuChoiceInvalidException("Edit Camp Menu");
        }
    }

}
