package cams.ui;

import cams.MainApp;
//import cams.object.Camp;
import cams.object.person.*;
import cams.ui.LoginMenuUI;
import cams.util.ScannerHelper;

import org.checkerframework.checker.units.qual.s;
import java.util.Scanner;


public class StudentMenuUI extends BaseUI{
    private Student currentStudent;
    private Scanner input = ScannerHelper.getScannerInput();

    public StudentMenuUI (Student student){
        this.currentStudent = student;
    }

    @Override
    protected int generateMenuScreen() {
        printHeader("Student Menu");
        System.out.println("1) Register For Camp");
        System.out.println("2) Withdraw From Camp");
        System.out.println("3) View All Camps"); //print list of camps and their remaining slots
        System.out.println("4) View Your Camps");
        System.out.println("5) Submit Enquiry");
        System.out.println("6) View Enquiries"); 
        System.out.println("7) Change Password");
        System.out.println("8) Exit Student Menu");
        printBreaks();

        int choice = doMenuChoice(10, 0);
        switch(choice){
            case 1:
                this.studentRegisterForCamp();
                break;
            case 2:
                this.studentWithdrawFromCamp();
                break;
            case 3:
                this.studentViewAllCamps();
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
                this.studentChangePassword(currentStudent);
                break;
            case 8:
                return -1;

            default:
                throw new MenuChoiceInvalidException("Student Menu");
        }
        return 0;
    }


    private void studentRegisterForCamp(){

    }


    private void studentWithdrawFromCamp(){

    }


    private void studentViewAllCamps(){

    }


    private void studentRegisteredCamps(){

    }


    private void studentSubmitEnquiry(){

    }


    private void studentViewEnquiries(){

    }


    private void studentChangePassword(Student student) {
        printBreaks();
        System.out.println("Enter your new password: ");
        student.changePassword(ScannerHelper.getNewPassword());
    }
}


//using LoginMenuUI.current
//