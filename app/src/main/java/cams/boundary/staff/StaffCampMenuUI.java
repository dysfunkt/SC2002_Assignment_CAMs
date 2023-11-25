package cams.boundary.staff;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


import cams.boundary.BaseUI;
import cams.boundary.modeldisplayer.ModelDisplayer;
import cams.controller.account.user.CurrentUser;
import cams.controller.camp.CampManager;
import cams.model.camp.Camp;
import cams.model.camp.eLocation;
import cams.model.person.*;
import cams.repository.appitem.CampRepository;
import cams.util.IDHelper;
import cams.util.exception.MenuChoiceInvalidException;
import cams.util.exception.ModelAlreadyExistsException;
import cams.util.exception.ModelNotFoundException;
import cams.util.exception.OperationCancelledException;
import cams.util.exception.ParticipantAlreadyRegisteredException;
import cams.util.ui.ScannerHelper;

/**
 * This Class provides a UI for Staff to manage camps
 */
public class StaffCampMenuUI extends BaseUI{

    private Scanner input = ScannerHelper.getScannerInput();

    
    /** 
     * @return int
     */
    protected int generateMenuScreen() {
        printHeader("Staff Camps Menu");
        System.out.println("1) Create Camp");
        System.out.println("2) Edit Camp");
        System.out.println("3) Delete Camp");
        System.out.println("4) View Camps");
        System.out.println("5) View Your Camps");
        System.out.println("6) Generate Camp Report");
        System.out.println("7) Return to Staff Menu");
        System.out.println("0) Exit Application");
        printBreaks();
        int choice = doMenuChoice(7, 0);
        switch (choice) {
            case 1:
                createCamp();
                break;
            case 2:
                editCamp();
                break;
            case 3:
                deleteCamp();
                break;
            case 4:
                viewAllCamps();
                break;
            case 5:
                viewYourCamps();
                break;
            case 6:
                generateReport();
                break;
            case 7:
                System.out.println("Switching back to Staff Menu.");
                return -1;
            case 0:
                System.out.println("Closing application...");
                return 1; //shutdown
            default:
                throw new MenuChoiceInvalidException("Staff Camps Menu");
        }
        return 0;
    }

    /**
     * This method allows staff to create a camp.
     */
    private void createCamp() {
        printHeader("Create Camp");
        String campName;
        Date startDate;
        Date endDate;
        Date regCloseDate;
        eFaculty userGroup;
        eLocation campLocation;
        int campTotalSlots;
        int campCommitteeSlots;
        String campDescription;
        Boolean visibility;

        System.out.print("Enter Camp Name: ");
        campName = input.nextLine();

        startDate = ScannerHelper.getDateInput("Enter the date (yyyy-MM-DD) that the camp starts: ");

        endDate = ScannerHelper.getDateInput("Enter the date (yyyy-MM-DD) that the camp ends: ");

        regCloseDate = ScannerHelper.getDateInput("Enter the date (yyyy-MM-DD) that the registration ends: ");

        int choice = ScannerHelper.getIntegerInput("Set group camp is open to (1 for your faculty, 2 for whole NTU): ", 0 ,3);
        switch (choice) {
            case 1:
                userGroup = CurrentUser.get().getFaculty();
                break;
            case 2:
                userGroup = Enum.valueOf(eFaculty.class, "NTU");
                break;
            default:
                userGroup = CurrentUser.get().getFaculty();
        }

        System.out.print("Enter Camp Location: ");
        campLocation = ScannerHelper.getLocationInput();

        campTotalSlots = ScannerHelper.getIntegerInput("Enter total # of slots for students: ", 0);

        campCommitteeSlots = ScannerHelper.getIntegerInput("Enter total # of slots for camp committee (Max 10): ", 0, 11);

        System.out.print("Enter Camp Description: ");
        campDescription = input.nextLine();

        visibility = ScannerHelper.getYesNoInput("Make camp visible?");

        printBreaks();
    
        try {
            CampManager.createCamp(campName, startDate, endDate, regCloseDate, userGroup, campLocation, campTotalSlots, campCommitteeSlots, campDescription, visibility);
        } catch (ModelAlreadyExistsException e) {
            System.out.println(e.getLocalizedMessage());
            return;
        }
        System.out.println("Camp created successfully.");
        return;
    }


    /**
     * This method allows the staff to choose the camp that they want to edit.
     */
    private void editCamp(){
        List<Camp> campList;
        try{
            campList = CampRepository.getInstance().getByIDList(((Staff)CurrentUser.get()).getCampsInChargeID());      
        } catch (ModelNotFoundException e) {
            System.out.println(e.getLocalizedMessage());
            return;
        }
        if (campList.size() == 0) {
            System.out.println("No Camps Available! Returning to Camp Menu...");
            return;
        }
        System.out.println("List of camps under your charge: ");
        System.out.println();
        Collections.sort(campList, Comparator.comparing(Camp::getCampName));
        ModelDisplayer.displayListOfDisplayable(campList);
        printBreaks();
        String campID = ScannerHelper.getIDInput("Enter the ID of the camp to edit (Enter 0 to cancel): ", IDHelper.extractCampIDs(campList),"Enter one of the IDs!");
        if (campID.equals("0")) {
            System.out.println("Cancelling edit. Returning to Camp Menu...");
            return;
        }
        
        new StaffEditCampMenuUI(campID).startMainMenu();
    }


    /**
     * This method allows staff to delete a camp.
     */
    private void deleteCamp(){
        List<Camp> campList;
        try{
            campList = CampRepository.getInstance().getByIDList(((Staff)CurrentUser.get()).getCampsInChargeID());
        } catch (ModelNotFoundException e) {
            System.out.println(e.getLocalizedMessage());
            return;
        }
        if (campList.size() == 0) {
            System.out.println("No Camps Available! Returning to Camp Menu...");
            return;
        }
        System.out.println("List of camps under your charge: ");
        System.out.println();
        Collections.sort(campList, Comparator.comparing(Camp::getCampName));
        ModelDisplayer.displayListOfDisplayable(campList);
        printBreaks();
        String campNo = ScannerHelper.getIDInput("Enter the ID of the camp to delete (Enter 0 to cancel): ", IDHelper.extractCampIDs(campList),"Enter one of the IDs!");
        if (campNo.equals("0")) {
            System.out.println("Cancelling delete. Returning to Camp Menu...");
            return;
        }
        try{
            CampManager.deleteCamp(campNo);
        } catch (ParticipantAlreadyRegisteredException e) {
            System.out.println(e.getLocalizedMessage());
            System.out.println("You cannot delete a camp that already have registered participants.");
            System.out.println("Cancelling delete. Returning to Camp Menu...");
        } catch (ModelNotFoundException e) {
            System.out.println(e.getLocalizedMessage());
            return;
        } catch (OperationCancelledException e) {
            System.out.println("Cancelling delete. Returning to Camp Menu...");

        }

        System.out.println("Camp deleted.");
    }


    /**
     * This method allows staff to view all camps.
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
                CampManager.getListByFilter();
                break;
            case 2:
                eFaculty facultyFilter = ScannerHelper.getFacultyInput();
                CampManager.getListByFilter(facultyFilter);
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
                throw new MenuChoiceInvalidException("Staff Camps Menu");
        }

        System.out.println("List of camps: ");
        System.out.println();
        Collections.sort(campsToDisplay, Comparator.comparing(Camp::getCampName));
        ModelDisplayer.displayListOfDisplayable(campsToDisplay);
    }


    /**
     * This method allows staff to view camps they are in charge of.
     */
    private void viewYourCamps() {
        List<Camp> campList;
        try{
            campList = CampRepository.getInstance().getByIDList(((Staff)CurrentUser.get()).getCampsInChargeID());
        } catch (ModelNotFoundException e) {
            System.out.println(e.getLocalizedMessage());
            return;
        }
        if (campList.size() == 0) {
            System.out.println("No Camps Available! Returning to Camp Menu...");
            return;
        }
        System.out.println("List of camps under your charge: ");
        System.out.println();
        Collections.sort(campList, Comparator.comparing(Camp::getCampName));
        ModelDisplayer.displayListOfDisplayable(campList);
    }


    /**
     * This method allows staff to choose a camp they want to generate a report for.
     */
    private void generateReport(){
        List<Camp> campList;
        try{
            campList = CampRepository.getInstance().getByIDList(((Staff)CurrentUser.get()).getCampsInChargeID());
        } catch (ModelNotFoundException e) {
            System.out.println(e.getLocalizedMessage());
            return;
        }
        if (campList.size() == 0) {
            System.out.println("No Camps Available! Returning to Camp Menu...");
            return;
        }
        System.out.println("List of camps under your charge: ");
        System.out.println();
        Collections.sort(campList, Comparator.comparing(Camp::getCampName));
        ModelDisplayer.displayListOfDisplayable(campList);
        printBreaks();
        String campNo = ScannerHelper.getIDInput("Enter the ID of the camp to generate report (Enter 0 to cancel): ", IDHelper.extractCampIDs(campList),"Enter one of the IDs!");
        if (campNo.equals("0")) {
            System.out.println("Cancelling print. Returning to Camp Menu...");
            return;
        }

        new StaffReportMenuUI(campNo).startMainMenu();
        
    }

}
