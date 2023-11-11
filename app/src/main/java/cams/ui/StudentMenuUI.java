package cams.ui;

import cams.MainApp;
<<<<<<< HEAD
import cams.object.Camp;
import cams.object.person.*;
import cams.util.ScannerHelper;
=======
import cams.object.appitem.Camp;
import cams.object.person.*;
>>>>>>> fabb6fec7474c08a61f3fda528bf8ba78271fc5b

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

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
<<<<<<< HEAD
        System.out.println("1) View All Camps");    //print list of camps AND their remaining slots
        System.out.println("2) Register For Camp");
        System.out.println("3) Withdraw From Camp");
=======
        System.out.println("1) Register For Camp");
        System.out.println("2) Withdraw From Camp");
        System.out.println("3) View All Camps"); 
>>>>>>> fabb6fec7474c08a61f3fda528bf8ba78271fc5b
        System.out.println("4) View Your Camps");
        System.out.println("5) Submit An Enquiry");
        System.out.println("6) View Your Enquiries"); 
        System.out.println("7) Change Password");
<<<<<<< HEAD
        System.out.println("0) Exit Student Menu");
=======
        System.out.println("8) Log out");
        System.out.println("0) Exit Application");
>>>>>>> fabb6fec7474c08a61f3fda528bf8ba78271fc5b
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
<<<<<<< HEAD
                this.studentChangePassword(currentStudent);
                return -1;
            case 0:
=======
                this.studentChangePassword();
                break;
            case 8:
                System.out.println("You have successfully logged out.");
>>>>>>> fabb6fec7474c08a61f3fda528bf8ba78271fc5b
                return -1;
            case 0:
                System.out.println("Closing application...");
                return 1; //shutdown
            default:
                throw new MenuChoiceInvalidException("Student Menu");
        }
        return 0;
    }


    private void studentViewAllCamps(){

    }


    private void studentRegisterForCamp(){

        System.out.println("Choose the Camp you want to join: ");
    }


    private void studentWithdrawFromCamp(){

    }


<<<<<<< HEAD
=======
    private void studentViewAllCamps(){
        try {
            CampCSVHelper campCSVHelper = CampCSVHelper.getInstance();
            List<Camp> camps = campCSVHelper.readFromCSV();

            for (Camp camp : camps) {
                //only print if visibility is true and the user group matches the current user's faculty
                if (camp.isVisibility() && camp.getUserGroup().equals(MainApp.currentUser.getFaculty())) {
            
                    System.out.println("CampName: " + camp.getCampName());
                    System.out.println("StartDate: " + camp.getStartDate());
                    System.out.println("EndDate: " + camp.getEndDate());
                    System.out.println("RegCloseDate: " + camp.getRegCloseDate());
                    System.out.println("CampLocation: " + camp.getCampLocation());
                    System.out.println("CampTotalSlots: " + camp.getCampTotalSlots());
                    System.out.println("CampDescription: " + camp.getCampDescription());
                    System.out.println("StaffInCharge: " + camp.getStaffInCharge());
                    System.out.println("ListOfAttendees: " + camp.getListOfAttendees());
            
                    //separate each camp with a line
                    System.out.println("--------------------------");
                }
            } 
        }   catch (IOException e) {e.printStackTrace();}
    }


>>>>>>> fabb6fec7474c08a61f3fda528bf8ba78271fc5b
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
<<<<<<< HEAD
}


//using LoginMenuUI.current
=======
}
>>>>>>> fabb6fec7474c08a61f3fda528bf8ba78271fc5b
