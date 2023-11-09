package cams.ui;

import cams.MainApp;
import cams.object.Camp;
import cams.object.person.*;
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
        System.out.println("1) View All Camps");    //print list of camps AND their remaining slots
        System.out.println("2) Register For Camp");
        System.out.println("3) Withdraw From Camp");
        System.out.println("4) View Your Camps");
        System.out.println("5) Submit An Enquiry");
        System.out.println("6) View Your Enquiries"); 
        System.out.println("7) Change Password");
        System.out.println("0) Exit Student Menu");
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
                this.studentChangePassword(currentStudent);
                return -1;
            case 0:
                return -1;

            default:
                throw new MenuChoiceInvalidException("Student Menu");
        }
        return 0;
    }


    private void studentViewAllCamps(){

    }


    private void studentRegisterForCamp(){

    }


    private void studentWithdrawFromCamp(){

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