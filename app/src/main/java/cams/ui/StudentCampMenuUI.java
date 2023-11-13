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


public class StudentCampMenuUI extends BaseUI{

    private Scanner input = ScannerHelper.getScannerInput();

    protected int generateMenuScreen() {
        printHeader("Camps Menu");
        System.out.println("1) View Camps");
        System.out.println("2) Register For Camp");
        System.out.println("3) Withdraw From Camp"); 
        System.out.println("4) View Registered Camps");
        System.out.println("5) Back to Student Menu");
        System.out.println("0) Exit Application");
        printBreaks();

        int choice = doMenuChoice(5, 0);

        switch (choice) {
            case 1:
                ViewAllCamps();
                break;
            case 2:
                if(registerForCamp()) return -1;
                break;
            case 3:
                studentWithdrawFromCamp();
                break;
            case 4:
                studentRegisteredCamps();
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

    private void ViewAllCamps() {
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

    private void studentRegisterForCamp() {
        System.out.println("Choose the Camp you want to join:");
    
        // Display available camps with numbers
        int index = 1;
        for (Camp camp : MainApp.camps) {
            System.out.println(index + ") " + camp.getCampName());
            index++;
        }
    
        // Get the camp number from the user
        int selectedCampNumber = ScannerHelper.getIntegerInput("Enter the Camp Number to join (0 to cancel): ");
    
        if (selectedCampNumber == 0) {
            System.out.println("Registration canceled.");
            return;
        }
    
        // Check if the selected camp number is within the valid range
        if (selectedCampNumber >= 1 && selectedCampNumber <= MainApp.camps.size()) {
            // Find the selected camp using the number
            Camp selectedCamp = MainApp.camps.get(selectedCampNumber - 1);
    
            // Check if the student is already registered for the camp
            if (selectedCamp.getListOfAttendees().contains(MainApp.currentUser.getUserID())) {
                System.out.println("You are already registered for this camp.");
            } else {
                // Check if there are available spots
                if (selectedCamp.getCampTotalSlots() > 0) {
                    // Register the student for the camp
                    selectedCamp.getListOfAttendees().add(MainApp.currentUser.getUserID());
                    System.out.println("Registration successful for " + selectedCamp.getCampName() + ".");
                } else {
                    System.out.println("Sorry, no available spots for this camp.");
                }
            }
        } else {
            System.out.println("Invalid Camp Number. Please enter a valid number.");
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

    private void studentWithdrawFromCamp() {
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

    private void studentRegisteredCamps() {
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
                System.out.println("=================");
            }
        }
    }

    private void signupForCommittee() {
        System.out.println("=== Sign Up for Camp Committee ===");
    
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
            System.out.println("You need to be registered for a camp to sign up for the committee.");
            return;
        } else {
            System.out.println("=== Your Registered Camps ===");
            int index = 1;
            for (Camp camp : registeredCamps) {
                System.out.println(index + ") " + camp.getCampName());
                index++;
            }
        }
    
        // Get the camp number from the user
        int selectedCampNumber = ScannerHelper.getIntegerInput("Enter the Camp Number to sign up for committee (0 to cancel): ");
    
        if (selectedCampNumber == 0) {
            System.out.println("Sign up for Committee canceled.");
            return;
        }
    
        // Check if the selected camp number is within the valid range
        if (selectedCampNumber >= 1 && selectedCampNumber <= registeredCamps.size()) {
            // Find the selected camp using the number
            Camp selectedCamp = registeredCamps.get(selectedCampNumber - 1);
    
            // Check if there are available committee slots
            int availableCommitteeSlots = selectedCamp.getCampCommitteeSlots() - selectedCamp.getListOfCampCommittees().size();
            if (availableCommitteeSlots > 0) {
                // Check if the user is already a committee member
                if (selectedCamp.getListOfCampCommittees().contains(MainApp.currentUser.getUserID())) {
                    System.out.println("You are already a committee member for your selected camp.");
                } else {
                    // Add the user to the committee members
                    selectedCamp.getListOfCampCommittees().add(MainApp.currentUser.getUserID());
                    System.out.println("Sign up for Camp Committee successful for " + selectedCamp.getCampName() + ".");
                }
            } else {
                System.out.println("Sorry, no available committee slots for this camp.");
            }
        } else {
            System.out.println("Invalid Camp Number. Please enter a valid number.");
        }
    }
}
