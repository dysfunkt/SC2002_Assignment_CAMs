package cams.boundary.campcommittee;

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
import cams.util.exception.MenuChoiceInvalidException;
import cams.util.exception.ModelNotFoundException;
import cams.util.id.IDHelper;
import cams.util.ui.ScannerHelper;


/**
 * This class provides a UI for camp committee members to manage camps.
 * This class extends the BaseUI class.
 */
public class CCCampMenuUI extends BaseUI{
    
    /**
     * Displays the UI for camp committee members to manage camps.
     * @return -1 to return to the previous menu, 1 to exit the application, otherwise 0.
     */
    protected int generateMenuScreen() {
        printHeader("Camp Committee Camps Menu");
        System.out.println("1) View Camps");
        System.out.println("2) Register For Camp");
        System.out.println("3) Withdraw From Camp");
        System.out.println("4) View Registered Camps");
        System.out.println("5) Return to Camp Committee Menu");
        System.out.println("0) Exit Application");
        printBreaks();
        int choice = doMenuChoice(5,0);
        switch(choice) {
            case 1:
                viewAllCamps();
                break;
            case 2:
                registerForCamp();
                break;
            case 3:
                withdrawFromCamp();   
                break;
            case 4:
                viewRegisteredCamps();
                break;
            case 5:
                System.out.println("Switching back to Camp Committee Menu.");
                return -1;
            case 0:
                System.out.println("Closing application...");
                return 1; //shutdown      
            default:
                throw new MenuChoiceInvalidException("Camp Committee Camps Menu");
        }
        return 0;
    }

    /**
     * Displays all available camps based on user specific filters.
     * The resulting list of camps is sorted alphabetically and displayed using ModelDisplayer.
     */
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
            campsToDisplay.addAll(CampManager.getListByFilter());
                break;
            case 2:
                int facultyFilter = ScannerHelper.getIntegerInput("Your faculty (1) or NTU (2)", 0, 3);
                switch (facultyFilter) {
                    case 1:
                    campsToDisplay.addAll(CampManager.getListByFilter(CurrentUser.get().getFaculty()));
                        break;
                    case 2:
                    campsToDisplay.addAll(CampManager.getListByFilter(Enum.valueOf(eFaculty.class, "NTU")));
                        break;
                    default:
                        break;
                }
                break;
            case 3:
                eLocation locationFilter = ScannerHelper.getLocationInput();
                campsToDisplay.addAll(CampManager.getListByFilter(locationFilter));
                break;
            case 4:
                Date startDate = ScannerHelper.getDateInput("From (yyyy-MM-dd): ");
                Date endDate = ScannerHelper.getDateInput("To (yyyy-MM-dd): ");
                campsToDisplay.addAll(CampManager.getListByFilter(startDate, endDate));
                break;
            case 5: 
                Date closingDate = ScannerHelper.getDateInput("Registration closing date by (yyyy-MM-dd): ");
                campsToDisplay.addAll(CampManager.getListByFilter(closingDate));
                break;
            case 0:
                return;
            default:
                break;
        }
        printBreaks();
        Collections.sort(campsToDisplay, Comparator.comparing(Camp::getCampName));
        System.out.println("List of camps: ");
        ModelDisplayer.displayListOfDisplayable(campsToDisplay);
    }

    /**
     * Allows camp committee member to register for another camp by choosing from the list of available camps.
     * Check if the user has registered and withdrew from the same camp in the past and restrict registration
     */
    private void registerForCamp() {
        printHeader("Register for Camp");
        List<Camp> campList = CampManager.getListByFilter();
        if (campList.size() == 0) {
            System.out.println("No Camps Available! Returning to Camp Menu...");
            return;
        }
        Collections.sort(campList, Comparator.comparing(Camp::getCampName));
        ModelDisplayer.displayListOfDisplayable(campList);
        printBreaks();
        String campChoice = ScannerHelper.getIDInput("Enter ID of camp to register (Enter 0 to cancel): ", IDHelper.extractCampIDs(campList), "Enter one of the IDs!");
        if (campChoice.equals("0") ) {
            System.out.println("Registration cancelled. Returning to Camp Menu...");
            return;
        }
        //do all the checks
        try{
            if (!StudentManager.joinCampCheck(campChoice)) {
                System.out.println("Unable to join camp. Returning to Camp Menu...");
                return;
            }
            StudentManager.joinAsAttendee(campChoice);
            System.out.println("Joined camp as Attendee successfully!");
        } catch (ModelNotFoundException e) {
            System.out.println("Error in joining camp. Returning to Camp Menu...");
            return;
        }
        
    }


    /**
     *Allows student to withdraw from a camp by choosing from the list of their registered camps.
     */
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
        }
    }

    /**
     * Displays the list of camps that the student is currently registered for.
     */
    private void viewRegisteredCamps() {
        printHeader("Registered Camps");
        List<Camp> campList;
        try {
            campList = CampRepository.getInstance().getByIDList(((Student)CurrentUser.get()).getJoinedCamps());
        } catch (ModelNotFoundException e) {
            System.out.println(e.getLocalizedMessage());
            return;
        }
        if (!((Student)CurrentUser.get()).getCampIDCommittingFor().isEmpty()){
            try{
                campList.add(CampRepository.getInstance().getByID(((Student)CurrentUser.get()).getCampIDCommittingFor()));
            } catch (ModelNotFoundException e) {
                System.out.println(e.getLocalizedMessage());
            }
        }
        if (campList.size() == 0) {
            System.out.println("No Camps Available! Returning to Camp Menu...");
            return;
        }
        Collections.sort(campList, Comparator.comparing(Camp::getCampName));
        ModelDisplayer.displayListOfDisplayable(campList);
    }
}