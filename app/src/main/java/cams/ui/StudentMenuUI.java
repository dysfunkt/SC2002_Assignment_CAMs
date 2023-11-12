package cams.ui;

import cams.MainApp;
import cams.object.appitem.Camp;
import cams.object.appitem.Enquiry;
import cams.object.person.*;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

import cams.util.CampCSVHelper;
import cams.util.ScannerHelper;


public class StudentMenuUI extends BaseUI{
    //private Student currentStudent;
    private Scanner input = ScannerHelper.getScannerInput();

    //public StudentMenuUI (Student student){
    //this.currentStudent = student;
    //}

    @Override
    protected int generateMenuScreen() {
        printHeader("Student Menu");
        System.out.println("1) Manage Camps");
        System.out.println("2) Manage Enquiries");
        System.out.println("3) Change Password"); 
        System.out.println("4) Log out");
        System.out.println("5) Exit Application");
        printBreaks();

        int choice = doMenuChoice(5, 0);
        switch(choice){
            case 1:
                generateCampsMenu(); //samtan dying
                break;
            case 2:
                generateEnquiriesMenu();
                break;
            case 3:
                this.studentChangePassword();
                break;
            case 4:
                System.out.println("You have successfully logged out.");
                return -1;
            case 5:
            
                System.out.println("Closing application...");
                return 1; //shutdown
            default:
                throw new MenuChoiceInvalidException("Student Menu");
            }
        
        return 0;
    }
    
    private void generateCampsMenu() {
        while (true) {  // Add a loop to stay in the submenu
            printHeader("Manage Camps");
            System.out.println("1) View All Camps");
            System.out.println("2) Register For Camp");
            System.out.println("3) Withdraw From Camp"); 
            System.out.println("4) View Registered Camps");
            System.out.println("5) Sign up for camp committee");
            System.out.println("6) Back to Student Menu");
            printBreaks();
    
            int choice = doMenuChoice(6, 0);
    
            switch (choice) {
                case 1:
                    studentViewAllCamps();
                    break;
                case 2:
                    studentRegisterForCamp();
                    break;
                case 3:
                    studentWithdrawFromCamp();
                    break;
                case 4:
                    studentRegisteredCamps();
                    break;
                case 5:
                    signupForCommittee();
                    break;
                case 6:
                    return;  // Exit the loop and go back to the Student Menu
                default:
                    throw new MenuChoiceInvalidException("Camps Menu");
            }
        }
    }
    
    private void generateEnquiriesMenu() {
        while (true) {  // Add a loop to stay in the submenu
            printHeader("Manage Enquiries");
            System.out.println("1) Submit Enquiries");
            System.out.println("2) View Enquiries");
            System.out.println("3) Delete Enquiries");
            System.out.println("4) Back to Student Menu");
            printBreaks();
    
            int choice = doMenuChoice(4, 0);
    
            switch (choice) {
                case 1:
                    studentSubmitEnquiry();
                    break;
                case 2:
                    studentViewEnquiries();
                    break;
                case 3:
                    deleteEnquiries();
                    break;    
                case 4:
                    return;  // Exit the loop and go back to the Student Menu
                default:
                    throw new MenuChoiceInvalidException("Enquiries Menu");
            }
        }
    }
    


    private void studentViewAllCamps() {
        System.out.println("=== All Available Camps ===");
    
        // Get the current student
        Student currentStudent = (Student) MainApp.currentUser;
    
        // Create an ArrayList to store eligible camps
        ArrayList<Camp> eligibleCamps = new ArrayList<>();
    
        // Loop through all camps
        for (Camp camp : MainApp.camps) {
            // Check eligibility criteria (e.g., if the user group is the same as the current student's faculty or "NTU")
            if (camp.getUserGroup() == eFaculty.NTU || camp.getUserGroup() == currentStudent.getFaculty()) {
                eligibleCamps.add(camp);
            }
        }
        
        // Display the eligible camps
        if (eligibleCamps.isEmpty()) {
            System.out.println("No camps available for your faculty.");
        } else {
            for (Camp camp : eligibleCamps) {
                System.out.println("Camp ID: " + MainApp.uniqueID.getNextCampID());
                System.out.println("Camp Name: " + camp.getCampName());
                System.out.println("Start Date: " + camp.getStartDate());
                System.out.println("End Date: " + camp.getEndDate());
                System.out.println("Capacity: " + camp.getCampTotalSlots());
                System.out.println("Available Spots: " + (camp.getCampTotalSlots() - camp.getListOfAttendees().size()));
                System.out.println("=================");
            }
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
            } else if (selectedCamp.getLeavers().contains(MainApp.currentUser.getUserID())) {
                System.out.println("You cannot register for this camp as you have previously withdrawn from it.");
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
                // Add the student's name to the leavers list
                selectedCamp.getLeavers().add(MainApp.currentUser.getUserID());
    
                // Increment total slots and withdraw the student from the camp
                selectedCamp.setCampTotalSlots(selectedCamp.getCampTotalSlots() + 1);
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
    
        // Check if the user is already in a committee
        for (Camp camp : MainApp.camps) {
            if (camp.getListOfCampCommittees().contains(currentStudent.getUserID())) {
                System.out.println("You are already a committee member for the camp '" + camp.getCampName() + "'.");
                return;
            }
        }
    
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
                // Add the user to the committee members
                selectedCamp.getListOfCampCommittees().add(currentStudent.getUserID());
                System.out.println("Sign up for Camp Committee successful for " + selectedCamp.getCampName() + ".");
            } else {
                System.out.println("Sorry, no available committee slots for this camp.");
            }
        } else {
            System.out.println("Invalid Camp Number. Please enter a valid number.");
        }
    }
    
    
    
    

    private void studentSubmitEnquiry() {
        printHeader("Submit Enquiry");
    
        // Get the current student
        Student currentStudent = (Student) MainApp.currentUser;
    
        // Use the standard Scanner class to get the enquiry message from the user
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your enquiry message: ");
        String enquiryMessage = scanner.nextLine();
    
        // Increment the enquiryID using UniqueID class
        int newEnquiryID = MainApp.uniqueID.getNextEnquiryID();
        MainApp.uniqueID.incrementEnquiryID();
    
        // Create a new Enquiry object
        Enquiry newEnquiry = new Enquiry(newEnquiryID, 0, currentStudent.getUserID(), enquiryMessage);
    
        // Add the new enquiry to the list of enquiries
        MainApp.enquiries.add(newEnquiry);
    
        // Print out the new enquiryID
        System.out.println("Enquiry submitted successfully. Your Enquiry ID is: " + newEnquiryID);
        printBreaks();
    }
    


private void studentViewEnquiries() {
    printHeader("View Enquiries");

    // Get the current student
    Student currentStudent = (Student) MainApp.currentUser;

    // Display the enquiries submitted by the current student
    int index = 1;
    for (Enquiry enquiry : MainApp.enquiries) {
        if (enquiry.getCreatedBy().equals(currentStudent.getUserID())) {
            System.out.println("Enquiry ID: " + enquiry.getEnquiryID());
            System.out.println("Camp ID: " + enquiry.getCampID());
            System.out.println("Enquiry Message: " + enquiry.getEnquiryMessage());
            System.out.println("Processed: " + enquiry.isProcessed());
            System.out.println("Reply Viewed: " + enquiry.isReplyViewed());
            System.out.println("=================");
            index++;
        }
    }

    // Check if there are no enquiries
    if (index == 1) {
        System.out.println("You haven't submitted any enquiries.");
    }

    printBreaks();
    }   

    private void deleteEnquiries() {
        // Get the current student
        Student currentStudent = (Student) MainApp.currentUser;
    
        // Display the enquiries submitted by the current student
        int index = 1;
        for (Enquiry enquiry : MainApp.enquiries) {
            if (enquiry.getCreatedBy().equals(currentStudent.getUserID())) {
                System.out.println("Enquiry ID: " + enquiry.getEnquiryID());
                System.out.println("Camp ID: " + enquiry.getCampID());
                System.out.println("Enquiry Message: " + enquiry.getEnquiryMessage());
                System.out.println("Processed: " + enquiry.isProcessed());
                System.out.println("Reply Viewed: " + enquiry.isReplyViewed());
                System.out.println("=================");
                index++;
            }
        }
    
        // Check if there are no enquiries
        if (index == 1) {
            System.out.println("You haven't submitted any enquiries.");
            return;
        }
    
        // Get the Enquiry ID to delete from the user
        int enquiryToDelete = ScannerHelper.getIntegerInput("Enter the Enquiry ID to delete (0 to cancel): ");
    
        if (enquiryToDelete == 0) {
            System.out.println("Deletion canceled.");
            return;
        }
    
        // Find the Enquiry using the ID
        Enquiry enquiryToDeleteObject = null;
        for (Enquiry enquiry : MainApp.enquiries) {
            if (enquiry.getEnquiryID() == enquiryToDelete && enquiry.getCreatedBy().equals(currentStudent.getUserID())) {
                enquiryToDeleteObject = enquiry;
                break;
            }
        }
    
        // Check if the Enquiry was found
        if (enquiryToDeleteObject != null) {
            // Remove the Enquiry from the list
            MainApp.enquiries.remove(enquiryToDeleteObject);
            System.out.println("Enquiry deleted successfully.");
        } else {
            System.out.println("Invalid Enquiry ID or you don't have permission to delete this enquiry.");
        }
    
        printBreaks();
    }
    

    private void studentChangePassword() {
        printBreaks();
        System.out.println("Enter your new password: ");
        MainApp.currentUser.changePassword(ScannerHelper.getNewPassword());
    }
}
