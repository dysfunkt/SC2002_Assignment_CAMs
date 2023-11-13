package cams.ui;

import java.util.Date;
import java.util.Scanner;

import cams.MainApp;
import cams.object.appitem.Camp;
import cams.object.appitem.eLocation;
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
        System.out.println("7) Total Number of Participants");
        System.out.println("8) Total Number of Committee Slots");
        System.out.println("9) Camp Description");
        System.out.println("10) Visibility");
        System.out.println("0) Cancel edit");
        printBreaks();
        int choice = doMenuChoice(11, 0);
        input.nextLine();
        switch (choice) {
            case 1:
                String campName;

                // In case the previous input was a primitive data type
                input.nextLine();

                // Set camp details
                System.out.print("Enter Camp Name: ");
                campName = input.nextLine();
                campToEdit.setCampName(campName);
                System.out.println("Camp Name edited successfully.");
                return -1;
            case 2:
                Date startDate;
                startDate = ScannerHelper.getDateInput("Enter the date (yyyy-MM-DD) that the camp starts (terminate with 0): ");
                campToEdit.setStartDate(startDate);
                System.out.println("Camp Start Date edited successfully.");
                return -1;
            case 3:
                Date endDate;
                endDate = ScannerHelper.getDateInput("Enter the date (yyyy-MM-DD) that the camp starts (terminate with 0): ");
                campToEdit.setEndDate(endDate);
                System.out.println("Camp End Date edited successfully.");
                return -1;
            case 4:
                Date regCloseDate;
                regCloseDate = ScannerHelper.getDateInput("Enter the date (yyyy-MM-DD) that the registration ends (terminate with 0): ");
                campToEdit.setRegCloseDate(regCloseDate);
                System.out.println("Camp Registration Close Date edited successfully.");
                return -1;
            case 5:
                eFaculty userGroup;
                int groupChoice = ScannerHelper.getIntegerInput("Set group camp is open to (1 for your faculty, 2 for whole NTU): ", 0 ,3);
                switch (groupChoice) {
                    case 1:
                        userGroup = MainApp.currentUser.getFaculty();
                        campToEdit.setUserGroup(userGroup);
                    case 2:
                        userGroup = Enum.valueOf(eFaculty.class, "NTU");
                        campToEdit.setUserGroup(userGroup);
                }
                System.out.println("Camp Faculty edited successfully.");
                return -1;

            case 6:
                eLocation campLocation = ScannerHelper.getLocationInput();
                campToEdit.setCampLocation(campLocation);
                System.out.println("Camp Location edited successfully.");
                return -1;
            case 7:
                int campTotalSlots;
                campTotalSlots = ScannerHelper.getIntegerInput("Enter total # of slots for participants: ");
                campToEdit.setCampTotalSlots(campTotalSlots);
                System.out.println("Camp Participant Slots edited successfully.");
                return -1;
            case 8:
                int campCommitteeSlots;
                campCommitteeSlots = ScannerHelper.getIntegerInput("Enter total # of slots for camp committee (Max 10): ", 0, 11);
                campToEdit.setCampCommitteeSlots(campCommitteeSlots);
                System.out.println("Camp Committee Slots edited successfully.");
                return -1;
            case 9:
                String campDescription;
                System.out.print("Enter Camp Description: ");
                campDescription = input.nextLine();
                campToEdit.setCampDescription(campDescription);
                System.out.println("Camp Description edited successfully.");
                return -1;
            case 10:
                Boolean visibility;
                visibility = ScannerHelper.getYesNoInput("Make camp visible?");
                if (!visibility) {
                    if (campToEdit.getListOfAttendees().size() != 0 || campToEdit.getListOfCampCommittees().size() != 0) {
                        System.out.println("You cannot make a camp with registered participants not visible.");
                        System.out.println("Cancelling edit. Returning to Camp Menu...");
                        return -1;
                    }
                }
                campToEdit.setVisibility(visibility);
                System.out.println("Camp Visibility edited successfully.");
                return -1;
            case 0:
                System.out.println("Cancelling edit. Returning to Camp Menu...");
                return -1;
            default:
                throw new MenuChoiceInvalidException("Staff Edit Camps Menu");
        }
    }

}
