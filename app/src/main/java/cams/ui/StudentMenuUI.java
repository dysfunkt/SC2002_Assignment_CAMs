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
                return 1;
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



    private void studentRegisterForCamp(){

        System.out.println("Choose the Camp you want to join: ");
    }


    private void studentWithdrawFromCamp(){

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
    


    private void studentRegisteredCamps(){
        
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
