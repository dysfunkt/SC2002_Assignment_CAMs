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


public class CCCampMenuUI extends BaseUI{
    private Scanner input = ScannerHelper.getScannerInput();

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
                    if((camp.getUserGroup().equals(MainApp.currentUser.getFaculty()) || camp.getUserGroup().equals(Enum.valueOf(eFaculty.class, "NTU"))) 
                        && camp.isVisibility()) {
                        campsToDisplay.add(camp);
                    }
                }
                break;
            case 2:
                int facultyFilter = ScannerHelper.getIntegerInput("Your faculty (1) or NTU (2)", 0, 3);
                switch (facultyFilter) {
                    case 1:
                        for (Camp camp : MainApp.camps) {
                            if (camp.getUserGroup().equals(MainApp.currentUser.getFaculty()) && camp.isVisibility()) 
                                campsToDisplay.add(camp);
                        }
                        break;
                    case 2:
                        for (Camp camp : MainApp.camps) {
                            if (camp.getUserGroup().equals(Enum.valueOf(eFaculty.class, "NTU")) && camp.isVisibility()) 
                                campsToDisplay.add(camp);
                        }
                        break;
                    default:
                        break;
                }
                break;
            case 3:
                eLocation locationFilter = ScannerHelper.getLocationInput();
                for (Camp camp : MainApp.camps) {
                    if ((camp.getUserGroup().equals(MainApp.currentUser.getFaculty()) || camp.getUserGroup().equals(Enum.valueOf(eFaculty.class, "NTU"))) 
                        && camp.isVisibility()
                        && camp.getCampLocation().equals(locationFilter)) {
                        campsToDisplay.add(camp);
                    }
                }
                break;
            case 4:
                Date startDate = ScannerHelper.getDateInput("From (yyyy-MM-dd): ");
                Date endDate = ScannerHelper.getDateInput("To (yyyy-MM-dd): ");
                for (Camp camp : MainApp.camps) {
                    if ((camp.getStartDate().after(startDate) || camp.getStartDate().equals(startDate)) 
                        && (camp.getEndDate().before(endDate) || camp.getEndDate().equals(endDate))
                        &&(camp.getUserGroup().equals(MainApp.currentUser.getFaculty()) || camp.getUserGroup().equals(Enum.valueOf(eFaculty.class, "NTU"))) 
                        && camp.isVisibility()) {
                        campsToDisplay.add(camp);    
                    }
                } 
                break;
            case 5: 
                Date closingDate = ScannerHelper.getDateInput("Registration closing date by (yyyy-MM-dd): ");
                for (Camp camp : MainApp.camps) {
                    if (camp.getRegCloseDate().before(closingDate) || camp.getRegCloseDate().equals(closingDate)
                        &&(camp.getUserGroup().equals(MainApp.currentUser.getFaculty()) || camp.getUserGroup().equals(Enum.valueOf(eFaculty.class, "NTU"))) 
                        && camp.isVisibility()) {
                        campsToDisplay.add(camp);
                    }
                } 
                break;
            case 0:
                return;
            default:
                break;
        }
        printBreaks();
        printListOfCamps(campsToDisplay);
    }


    private void printListOfCamps(ArrayList<Camp> list) {
        Collections.sort(list, Comparator.comparing(Camp::getCampName));
        String formatTemplate = "%-2s| %-10s| %-11s| %-11s| %-16s| %-8s| %-12s| %-16s| %-16s| %s";
        if (list.size()==0) {
            System.out.println("No Camps to Display!");
            return;
        }
        System.out.println(String.format(formatTemplate, "ID", "Camp Name", "Start Date", "End Date", "Reg. Close Date", "Faculty", "Location", "Att. slots left", "Com. slots left", "Description"));
        for (Camp camp : list) {
            System.out.println(String.format(formatTemplate, camp.getCampID(), camp.getCampName(), CSVStringHelper.DateToCSVString(camp.getStartDate()), CSVStringHelper.DateToCSVString(camp.getEndDate()), CSVStringHelper.DateToCSVString(camp.getRegCloseDate()), camp.getUserGroup() + "", camp.getCampLocation() + "", camp.remainingAttendeeSlots() + "", camp.remainingCommitteeSlots()+"", camp.getCampDescription()));
        }
    }
    

    private Boolean registerForCamp() {
        ArrayList<Camp> campsToDisplay = new ArrayList<>();
        //fetch all camps student can see
        for (Camp camp : MainApp.camps) {
            if((camp.getUserGroup().equals(MainApp.currentUser.getFaculty()) || camp.getUserGroup().equals(Enum.valueOf(eFaculty.class, "NTU"))) 
                && camp.isVisibility()) {
                campsToDisplay.add(camp);
            }
        }
        if (campsToDisplay.size() == 0) {
            System.out.println("No Camps Available! Returning to Camp Menu...");
            return false;
        }
        printListOfCamps(campsToDisplay);

        int campChoice = ScannerHelper.getIntegerInput("Enter ID of camp to register (Enter 0 to cancel): ", IDHelper.extractCampIDs(campsToDisplay), "Enter one of the IDs!");
        if (campChoice == 0) {
            System.out.println("Registration cancelled. Returning to Camp Menu...");
            return false;
        }
        //do all the checks
        if (((Student)MainApp.currentUser).checkJoined(campChoice)) {
            System.out.println("You have already registered for this camp. Returning to Camp Menu...");
            return false;
        }
        if (MainApp.todayDate.after(IDHelper.getCampFromID(campChoice).getRegCloseDate())) {
            System.out.println("Registration for this camp has closed. Returning to Camp Menu...");
            return false;
        }
        if (IDHelper.getCampFromID(campChoice).getLeavers().contains(MainApp.currentUser.getUserID())) {
            System.out.println("You withdrew from this camp before. Registering again is not allowed. Returning to Camp Menu...");
            return false;
        }
        for (Integer i : ((Student)MainApp.currentUser).getJoinedCamps()) {
            if(IDHelper.getCampFromID(i).isClash(IDHelper.getCampFromID(campChoice).getStartDate(), IDHelper.getCampFromID(campChoice).getEndDate())) {
                System.out.println("The dates of this camp clashes with your joined camps. Returning to Camp Menu...");
                return false;
            }
        }
        if (IDHelper.getCampFromID(campChoice).remainingAttendeeSlots() == 0) {
            System.out.println("There are no more available slots in the camp. Returning to Camp Menu...");
            return false;
        }
        //end of checks
        int registerChoice = ScannerHelper.getIntegerInput("Register as Attendee (1) or Camp Committee (2): ", 0, 3);
        switch (registerChoice) {
            case 1:
                IDHelper.getCampFromID(campChoice).addAttendee(MainApp.currentUser.getUserID());
                ((Student)MainApp.currentUser).joinCamp(campChoice);
                System.out.println("Joined camp as Attendee successfully!");
                break;
            case 2:
                if (IDHelper.getCampFromID(campChoice).getCampCommitteeSlots() == 0) {
                    System.out.println("No more Committee slots left. Register as Attendee instead. Returning to Camp Menu...");
                    return false;
                }
                IDHelper.getCampFromID(campChoice).addCommittee(MainApp.currentUser.getUserID());
                ((Student)MainApp.currentUser).joinCommittee(campChoice);
                System.out.println("Joined camp as Camp Committee successfully!");
                System.out.println("You will be logged out automatically. Re-Login to gain Camp Committee privileges.");
                return true;
        }
        return false;
    }

    private void withdrawFromCamp() {
        System.out.println("=== Your Registered Camps ===");
    
        // Display camps the student is registered for with numbers
        int index = 1;
        for (Camp camp : MainApp.camps) {
            if (camp.getListOfAttendees().contains(MainApp.currentUser.getUserID())) {
                System.out.println(index + ") " + camp.getCampName());
                index++;
            }
        }
    
        // Get the camp number from the user
        int selectedCampNumber = ScannerHelper.getIntegerInput("Enter the Camp Number to withdraw from (0 to cancel): ");
    
        if (selectedCampNumber == 0) {
            System.out.println("Withdrawal canceled.");
            return;
        }
    
        // Check if the selected camp number is within the valid range
        if (selectedCampNumber >= 1 && selectedCampNumber <= index - 1) {
            // Find the selected camp using the number
            Camp selectedCamp = getRegisteredCampByNumber(selectedCampNumber);
    
            // Check if the student is registered for the camp (extra check for safety)
            if (selectedCamp != null && selectedCamp.getListOfAttendees().contains(MainApp.currentUser.getUserID())) {
                // Withdraw the student from the camp
                selectedCamp.getListOfAttendees().remove(MainApp.currentUser.getUserID());
                System.out.println("Withdrawal successful from " + selectedCamp.getCampName() + ".");
            } else {
                System.out.println("You are not registered for this camp.");
            }
        } else {
            System.out.println("Invalid Camp Number. Please enter a valid number.");
        }
    }
    
    private Camp getRegisteredCampByNumber(int number) {
        int count = 0;
        for (Camp camp : MainApp.camps) {
            if (camp.getListOfAttendees().contains(MainApp.currentUser.getUserID())) {
                count++;
                if (count == number) {
                    return camp;
                }
            }
        }
        return null;
    }

    private void viewRegisteredCamps() {
        System.out.println("=== Your Registered Camps ===");
    
        // Get the current student
        Student currentStudent = (Student) MainApp.currentUser;
    
        // Create an ArrayList to store registered camps
        ArrayList<Camp> registeredCamps = new ArrayList<>();
    
        // Loop through all camps
        for (Camp camp : MainApp.camps) {
            // Check if the current student is in the list of attendees
            if (camp.getListOfAttendees().contains(currentStudent.getUserID())) {
                registeredCamps.add(camp);
            }
        }
    
        // Display the registered camps
        if (registeredCamps.isEmpty()) {
            System.out.println("You haven't registered for any camps.");
        } else {
            for (Camp camp : registeredCamps) {
                System.out.println("Camp ID: " + MainApp.uniqueID.getNextCampID());
                System.out.println("Camp Name: " + camp.getCampName());
                System.out.println("Start Date: " + camp.getStartDate());
                System.out.println("End Date: " + camp.getEndDate());
                printBreaks();
            }
        }
    }
}