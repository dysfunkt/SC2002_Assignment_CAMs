package cams.boundary.staff;

import java.util.Date;
import java.util.Scanner;


import cams.boundary.BaseUI;
import cams.controller.account.user.CurrentUser;
import cams.controller.camp.CampManager;
import cams.model.camp.eLocation;
import cams.model.person.eFaculty;
import cams.util.exception.MenuChoiceInvalidException;
import cams.util.exception.ModelNotFoundException;
import cams.util.exception.ParticipantAlreadyRegisteredException;
import cams.util.ui.ScannerHelper;

/**
 * This class provides a UI for staff to edit camp details.
 */
public class StaffEditCampMenuUI extends BaseUI{
    private String campID;
    protected StaffEditCampMenuUI(String ID){
        this.campID = ID;
    }

    private Scanner input = ScannerHelper.getScannerInput();

    
    /** 
     * @return int
     */
    protected int generateMenuScreen() {
        printHeader("Camp Edit Menu");
        System.out.println("Choose detail to edit: ");
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
        System.out.println("0) Cancel Edit");
        printBreaks();
        int choice = doMenuChoice(10, 0);
        switch (choice) {
            case 1:
                String campName;
                System.out.print("Enter Camp Name: ");
                campName = input.nextLine();
                try {
                    CampManager.editName(campID, campName);
                } catch (ModelNotFoundException e) {
                    System.out.println("Edit failed.");
                    return -1;
                }
                System.out.println("Camp Name edited successfully.");
                return -1;
            case 2:
                Date startDate;
                startDate = ScannerHelper.getDateInput("Enter the date (yyyy-MM-DD) that the camp starts (terminate with 0): ");
                try {
                    CampManager.editStartDate(campID, startDate);
                } catch (ModelNotFoundException e) {
                    System.out.println("Edit failed.");
                    return -1;
                }
                System.out.println("Camp Start Date edited successfully.");
                return -1;
            case 3:
                Date endDate;
                endDate = ScannerHelper.getDateInput("Enter the date (yyyy-MM-DD) that the camp starts (terminate with 0): ");
                try {
                    CampManager.editEndDate(campID, endDate);
                } catch (ModelNotFoundException e) {
                    System.out.println("Edit failed.");
                    return -1;
                }
                System.out.println("Camp End Date edited successfully.");
                return -1;
            case 4:
                Date regCloseDate;
                regCloseDate = ScannerHelper.getDateInput("Enter the date (yyyy-MM-DD) that the registration ends (terminate with 0): ");
                try {
                    CampManager.editRegCloseDate(campID, regCloseDate);
                } catch (ModelNotFoundException e) {
                    System.out.println("Edit failed.");
                    return -1;
                }
                System.out.println("Camp Registration Close Date edited successfully.");
                return -1;
            case 5:
                eFaculty userGroup;
                int groupChoice = ScannerHelper.getIntegerInput("Set group camp is open to (1 for your faculty, 2 for whole NTU): ", 0 ,3);
                switch (groupChoice) {
                    case 1:
                        userGroup = CurrentUser.get().getFaculty();
                        try {
                            CampManager.editFaculty(campID, userGroup);
                        } catch (ModelNotFoundException e) {
                            System.out.println("Edit failed.");
                            return -1;
                }
                    case 2:
                        userGroup = Enum.valueOf(eFaculty.class, "NTU");
                        try {
                            CampManager.editFaculty(campID, userGroup);
                        } catch (ModelNotFoundException e) {
                            System.out.println("Edit failed.");
                            return -1;
                        }
                }
                System.out.println("Camp Faculty edited successfully.");
                return -1;

            case 6:
                eLocation campLocation = ScannerHelper.getLocationInput();
                try {
                    CampManager.editLocation(campID, campLocation);
                } catch (ModelNotFoundException e) {
                    System.out.println("Edit failed.");
                    return -1;
                }
                System.out.println("Camp Location edited successfully.");
                return -1;
            case 7:
                int campTotalSlots;
                campTotalSlots = ScannerHelper.getIntegerInput("Enter total # of slots for participants: ");
                try {
                    CampManager.editTotalSlots(campID, campTotalSlots);
                } catch (ModelNotFoundException e) {
                    System.out.println("Edit failed.");
                    return -1;
                }
                System.out.println("Camp Participant Slots edited successfully.");
                return -1;
            case 8:
                int campCommitteeSlots;
                campCommitteeSlots = ScannerHelper.getIntegerInput("Enter total # of slots for camp committee (Max 10): ", 0, 11);
                try {
                    CampManager.editCCSlots(campID, campCommitteeSlots);
                } catch (ModelNotFoundException e) {
                    System.out.println("Edit failed.");
                    return -1;
                }
                System.out.println("Camp Committee Slots edited successfully.");
                return -1;
            case 9:
                String campDescription;
                System.out.print("Enter Camp Description: ");
                campDescription = input.nextLine();
                try {
                    CampManager.editDescription(campID, campDescription);
                } catch (ModelNotFoundException e) {
                    System.out.println("Edit failed.");
                    return -1;
                }
                System.out.println("Camp Description edited successfully.");
                return -1;
            case 10:
                Boolean visibility;
                visibility = ScannerHelper.getYesNoInput("Make camp visible?");
                try {
                    CampManager.editVisibility(campID, visibility);
                } catch (ModelNotFoundException e) {
                    System.out.println("Edit failed.");
                    return -1;
                } catch (ParticipantAlreadyRegisteredException e) {
                    System.out.println("Edit failed. You cannot make a camp with resgistered participants invisible.");
                }
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
