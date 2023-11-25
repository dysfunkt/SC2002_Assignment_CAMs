package cams.boundary.student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import cams.boundary.BaseUI;
import cams.boundary.modeldisplayer.ModelDisplayer;
import cams.controller.account.user.CurrentUser;
import cams.controller.camp.CampManager;
import cams.controller.person.StudentManager;
import cams.model.camp.Camp;
import cams.model.camp.eLocation;
import cams.model.person.*;
import cams.repository.appitem.CampRepository;
import cams.util.IDHelper;
import cams.util.exception.MenuChoiceInvalidException;
import cams.util.exception.ModelNotFoundException;
import cams.util.ui.ScannerHelper;

public class StudentCampMenuUI extends BaseUI{

    
    /** 
     * @return int
     */
    protected int generateMenuScreen() {
        printHeader("Student Camps Menu");
        System.out.println("1) View Camps");
        System.out.println("2) Register for Camp");
        System.out.println("3) Withdraw from Camp"); 
        System.out.println("4) View Registered Camps");
        System.out.println("5) Return to Student Menu");
        System.out.println("0) Exit Application");
        printBreaks();
        int choice = doMenuChoice(5, 0);
        switch (choice) {
            case 1:
                viewAllCamps();
                break;
            case 2:
                if(registerForCamp()) return -1;
                break;
            case 3:
                withdrawFromCamp();
                break;
            case 4:
                viewRegisteredCamps();
                break;
            case 5:
                System.out.println("Switching back to Student Menu.");
                return -1;
            case 0:
                System.out.println("Closing application...");
                return 1; //shutdown
            default:
                throw new MenuChoiceInvalidException("Student Camps Menu");
        }
        return 0;
    }

    private void viewAllCamps() {
        printHeader("View Camps");
        System.out.println("Filter by: ");
        System.out.println("1) View All");
        System.out.println("2) Faculty");
        System.out.println("3) Location");
        System.out.println("4) Camp Dates");
        System.out.println("5) Registration Date");
        System.out.println("0) Cancel");
        printBreaks();
        int choice = doMenuChoice(6, 0);
        ArrayList<Camp> campsToDisplay = new ArrayList<>();
        switch (choice) {
            case 1:
                CampManager.getListByFilter();
                break;
            case 2:
                int facultyFilter = ScannerHelper.getIntegerInput("Your faculty (1) or NTU (2)", 0, 3);
                switch (facultyFilter) {
                    case 1:
                        CampManager.getListByFilter(CurrentUser.get().getFaculty());
                        break;
                    case 2:
                        CampManager.getListByFilter(Enum.valueOf(eFaculty.class, "NTU"));
                        break;
                    default:
                        break;
                }
                break;
            case 3:
                eLocation locationFilter = ScannerHelper.getLocationInput();
                CampManager.getListByFilter(locationFilter);
                break;
            case 4:
                Date startDate = ScannerHelper.getDateInput("From (yyyy-MM-dd): ");
                Date endDate = ScannerHelper.getDateInput("To (yyyy-MM-dd): ");
                CampManager.getListByFilter(startDate, endDate);
                break;
            case 5: 
                Date closingDate = ScannerHelper.getDateInput("Registration closing date by (yyyy-MM-dd): ");
                CampManager.getListByFilter(closingDate);
                break;
            case 0:
                return;
            default:
                break;
        }
        Collections.sort(campsToDisplay, Comparator.comparing(Camp::getCampName));
        System.out.println("List of camps: ");
        System.out.println();
        ModelDisplayer.displayListOfDisplayable(campsToDisplay);
    }

    private Boolean registerForCamp() {
        printHeader("Register for Camp");
        List<Camp> campList = CampManager.getListByFilter();
        if (campList.size() == 0) {
            System.out.println("No Camps Available! Returning to Camp Menu...");
            return false;
        }
        Collections.sort(campList, Comparator.comparing(Camp::getCampName));
        ModelDisplayer.displayListOfDisplayable(campList);
        printBreaks();
        String campChoice = ScannerHelper.getIDInput("Enter ID of camp to register (Enter 0 to cancel): ", IDHelper.extractCampIDs(campList), "Enter one of the IDs!");
        if (campChoice.equals("0") ) {
            System.out.println("Registration cancelled. Returning to Camp Menu...");
            return false;
        }
        //do all the checks
        try{
            if (!StudentManager.joinCampCheck(campChoice)) {
            System.out.println("Unable to join camp. Returning to Camp Menu...");
            return false;
            }
        
        int registerChoice = ScannerHelper.getIntegerInput("Register as Attendee (1) or Camp Committee (2): ", 0, 3);
        switch (registerChoice) {
            case 1:
                StudentManager.joinAsAttendee(campChoice);
                System.out.println("Joined camp as Attendee successfully!");
                break;
            case 2:          
                if (CampManager.getRemainingCCSlots(campChoice) == 0) {
                    System.out.println("No more Committee slots left. Register as Attendee instead. Returning to Camp Menu...");
                    return false;
                }
                StudentManager.joinAsCC(campChoice);
                System.out.println("Joined camp as Camp Committee successfully!");
                System.out.println("You will be logged out automatically. Re-Login to gain Camp Committee privileges.");
                return true;
            }
        } catch (ModelNotFoundException e) {
            System.out.println("Error in joining camp. Returning to Camp Menu...");
            return false;
        }
        return false;
    }
    
    private void withdrawFromCamp() {
        printHeader("Withdraw from Camp");
        List<Camp> campList;
        try {
            campList = CampRepository.getInstance().getByIDList(((Student)CurrentUser.get()).getJoinedCamps());
        } catch (ModelNotFoundException e) {
            System.out.println(e.getLocalizedMessage());
            return;
        }
        if (campList.size() == 0) {
            System.out.println("No Camps Available! Returning to Camp Menu...");
            return;
        }
        Collections.sort(campList, Comparator.comparing(Camp::getCampName));
        ModelDisplayer.displayListOfDisplayable(campList);
        printBreaks();
        String campChoice = ScannerHelper.getIDInput("Enter ID of camp to register (Enter 0 to cancel): ", IDHelper.extractCampIDs(campList), "Enter one of the IDs!");
        if (campChoice.equals("0") ) {
            System.out.println("Withdrawal cancelled. Returning to Camp Menu...");
            return;
        }
        try {
            if (!StudentManager.leaveCamp(campChoice)) {
                System.out.println("Withdrawal cancelled. Returning to Camp Menu...");
                return;
            }
        } catch (ModelNotFoundException e) {
            System.out.println("Error in withdrawing from camp. Returning to Camp Menu...");
            return;
        }
    }

    private void viewRegisteredCamps() {
        printHeader("Registered Camps");
        List<Camp> campList;
        try {
            campList = CampRepository.getInstance().getByIDList(((Student)CurrentUser.get()).getJoinedCamps());
        } catch (ModelNotFoundException e) {
            System.out.println(e.getLocalizedMessage());
            return;
        }
        if (campList.size() == 0) {
            System.out.println("No Camps Available! Returning to Camp Menu...");
            return;
        }
        Collections.sort(campList, Comparator.comparing(Camp::getCampName));
        ModelDisplayer.displayListOfDisplayable(campList);
    }
}