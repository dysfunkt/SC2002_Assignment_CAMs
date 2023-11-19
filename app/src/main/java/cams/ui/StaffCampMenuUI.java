package cams.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Scanner;

import cams.MainApp;
import cams.object.appitem.*;
import cams.object.person.*;
import cams.util.CSVStringHelper;
import cams.util.IDHelper;
import cams.util.ScannerHelper;

public class StaffCampMenuUI extends BaseUI{

    private Scanner input = ScannerHelper.getScannerInput();

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
        int choice = doMenuChoice(10, 0);
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


    private void createCamp() {
        int campID;
        String campName;
        Date startDate;
        Date endDate;
        Date regCloseDate;
        eFaculty userGroup;
        eLocation campLocation;
        int campTotalSlots;
        int campCommitteeSlots;
        String campDescription;
        String staffInCharge;
        Boolean visibility;

        // In case the previous input was a primitive data type
        input.nextLine();

        // Set camp details
        campID = MainApp.uniqueID.getNextCampID();
        MainApp.uniqueID.incrementCampID();

        System.out.print("Enter Camp Name: ");
        campName = input.nextLine();

        startDate = ScannerHelper.getDateInput("Enter the date (yyyy-MM-DD) that the camp starts (terminate with 0): ");

        endDate = ScannerHelper.getDateInput("Enter the date (yyyy-MM-DD) that the camp ends (terminate with 0): ");

        regCloseDate = ScannerHelper.getDateInput("Enter the date (yyyy-MM-DD) that the registration ends (terminate with 0): ");

        int choice = ScannerHelper.getIntegerInput("Set group camp is open to (1 for your faculty, 2 for whole NTU): ", 0 ,3);
        switch (choice) {
            case 1:
                userGroup = MainApp.currentUser.getFaculty();
                break;
            case 2:
                userGroup = Enum.valueOf(eFaculty.class, "NTU");
                break;
            default:
                userGroup = MainApp.currentUser.getFaculty();
        }

        System.out.print("Enter Camp Location: ");
        campLocation = ScannerHelper.getLocationInput();

        campTotalSlots = ScannerHelper.getIntegerInput("Enter total # of slots for students: ");

        campCommitteeSlots = ScannerHelper.getIntegerInput("Enter total # of slots for camp committee (Max 10): ", 0, 11);

        System.out.print("Enter Camp Description: ");
        campDescription = input.nextLine();

        staffInCharge = MainApp.currentUser.getUserID();

        visibility = ScannerHelper.getYesNoInput("Make camp visible?");
        Camp newCamp = new Camp(campID, campName, startDate, endDate, regCloseDate, userGroup, campLocation, campTotalSlots, campCommitteeSlots, campDescription, staffInCharge, visibility);
        MainApp.camps.add(newCamp);
        ((Staff)MainApp.currentUser).createCamp(newCamp.getCampID());
        System.out.println("Camp created successfully.");
    }


    private void editCamp(){
        viewYourCamps();
        ArrayList<Camp> campList = ((Staff)MainApp.currentUser).getCampsInCharge();
        int campNo = ScannerHelper.getIntegerInput("Enter the ID of the camp to edit (Enter 0 to cancel): ", IDHelper.extractCampIDs(campList),"Enter one of the IDs!");
        if (campNo == 0) {
            System.out.println("Cancelling edit. Returning to Camp Menu...");
            return;
        }
        Camp chosenCamp = IDHelper.getCampFromID(campNo);
        if (chosenCamp == null) return;
        new StaffEditCampMenuUI(chosenCamp).startMainMenu();
    }


    private void deleteCamp(){
        viewYourCamps();
        ArrayList<Camp> campList = ((Staff)MainApp.currentUser).getCampsInCharge();
        int campNo = ScannerHelper.getIntegerInput("Enter the ID of the camp to delete (Enter 0 to cancel): ", IDHelper.extractCampIDs(campList),"Enter one of the IDs!");
        if (campNo == 0) {
            System.out.println("Cancelling delete. Returning to Camp Menu...");
            return;
        }
        Camp chosenCamp = IDHelper.getCampFromID(campNo);
        if (chosenCamp == null) return;
        if (chosenCamp.getListOfAttendees().size() != 0 || chosenCamp.getListOfCampCommittees().size() != 0) {
            System.out.println("You cannot delete a camp with registered participants.");
            System.out.println("Cancelling delete. Returning to Camp Menu...");
            return;
        }
        if(!ScannerHelper.getYesNoInput("Confirm delete?")) {
            System.out.println("Cancelling delete. Returning to Camp Menu...");
            return;
        }
        ((Staff)MainApp.currentUser).deleteCamp(chosenCamp.getCampID());
        MainApp.camps.remove(chosenCamp);
        System.out.println("Camp deleted.");
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
        input.nextLine();
        ArrayList<Camp> campsToDisplay = new ArrayList<>();
        switch (choice) {
            case 1:
                for (Camp camp : MainApp.camps) {
                    campsToDisplay.add(camp);
                }
                break;
            case 2:
                eFaculty facultyFilter = ScannerHelper.getFacultyInput();
                for (Camp camp : MainApp.camps) {
                    if (camp.getUserGroup().equals(facultyFilter)) campsToDisplay.add(camp);
                }
                break;
            case 3:
                eLocation locationFilter = ScannerHelper.getLocationInput();
                for (Camp camp : MainApp.camps) {
                    if (camp.getCampLocation().equals(locationFilter)) campsToDisplay.add(camp);
                }
                break;
            case 4:
                Date startDate = ScannerHelper.getDateInput("From (yyyy-MM-dd): ");
                Date endDate = ScannerHelper.getDateInput("To (yyyy-MM-dd): ");
                for (Camp camp : MainApp.camps) {
                    if ((camp.getStartDate().after(startDate) || camp.getStartDate().equals(startDate)) && (camp.getEndDate().before(endDate) || camp.getEndDate().equals(endDate))) 
                        campsToDisplay.add(camp);
                } 
                break;
            case 5: 
                Date closingDate = ScannerHelper.getDateInput("Registration closing date by (yyyy-MM-dd): ");
                for (Camp camp : MainApp.camps) {
                    if (camp.getRegCloseDate().before(closingDate) || camp.getRegCloseDate().equals(closingDate)) 
                        campsToDisplay.add(camp);
                } 
                break;
            case 0:
                return;
            default:
                throw new MenuChoiceInvalidException("Staff Camps Menu");
        }
        printBreaks();
        printListOfCamps(campsToDisplay);
    }


    private void viewYourCamps() {
        ArrayList<Camp> campsInCharge = ((Staff)MainApp.currentUser).getCampsInCharge();

        System.out.println("List of camps under your charge: ");
        printBreaks();
        printListOfCamps(campsInCharge);
    }


    private void generateReport(){
        viewYourCamps();
        ArrayList<Camp> campList = ((Staff)MainApp.currentUser).getCampsInCharge();
        int campNo = ScannerHelper.getIntegerInput("Enter the ID of the camp to generate report (Enter 0 to cancel): ", IDHelper.extractCampIDs(campList),"Enter one of the IDs!");
        if (campNo == 0) {
            System.out.println("Cancelling print. Returning to Camp Menu...");
            return;
        }
        Camp chosenCamp = IDHelper.getCampFromID(campNo);

        new StaffReportMenuUI(chosenCamp).startMainMenu();
        
    }
    

    private void printListOfCamps(ArrayList<Camp> list) {
        Collections.sort(list, Comparator.comparing(Camp::getCampName));
        String formatTemplate = "%-3s| %-20s| %-11s| %-11s| %-16s| %-8s| %-12s| %-16s| %-16s| %s";
        if (list.size()==0) {
            System.out.println("No Camps to Display!");
            return;
        }
        System.out.println(String.format(formatTemplate, "ID", "Camp Name", "Start Date", "End Date", "Reg. Close Date", "Faculty", "Location", "Att. slots left", "Com. slots left", "Description"));
        for (Camp camp : list) {
            System.out.println(String.format(formatTemplate, camp.getCampID(), camp.getCampName(), CSVStringHelper.DateToCSVString(camp.getStartDate()), CSVStringHelper.DateToCSVString(camp.getEndDate()), CSVStringHelper.DateToCSVString(camp.getRegCloseDate()), camp.getUserGroup() + "", camp.getCampLocation() + "", camp.remainingAttendeeSlots() + "", camp.remainingCommitteeSlots()+"", camp.getCampDescription()));
        }
    }
}
