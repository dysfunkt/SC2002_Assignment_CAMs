package cams.ui;

import cams.MainApp;
import cams.object.appitem.Camp;
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
        System.out.println("1) View All Camp");
        System.out.println("2) Register For Camp");
        System.out.println("3) Withdraw From Camps"); 
        System.out.println("4) View Your Camps");
        System.out.println("5) Submit An Enquiry");
        System.out.println("6) View Your Enquiries"); 
        System.out.println("7) Change Password");
        System.out.println("8) Log out");
        System.out.println("0) Exit Application");
        printBreaks();

        int choice = doMenuChoice(10, 0);
        switch(choice){
            case 1:
                this.studentViewAllCamps(); //samtan dying
                break;
            case 2:
                this.studentRegisterForCamp();
                break;
            case 3:
                this.studentWithdrawFromCamp();
                break;
            case 4:
                this.studentRegisteredCamps();
                break;
            case 5:
                this.studentSubmitEnquiry();
                break;
            case 6:
                this.studentViewEnquiries();
                break;
            case 7:
                this.studentChangePassword();
                break;
            case 8:
                System.out.println("You have successfully logged out.");
                return -1;
            case 0:
                System.out.println("Closing application...");
                return 1; //shutdown
            default:
                throw new MenuChoiceInvalidException("Student Menu");
        }
        return 0;
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


    private void studentSubmitEnquiry(){

    }


    private void studentViewEnquiries(){

    }


    private void studentChangePassword() {
        printBreaks();
        System.out.println("Enter your new password: ");
        MainApp.currentUser.changePassword(ScannerHelper.getNewPassword());
    }
}
