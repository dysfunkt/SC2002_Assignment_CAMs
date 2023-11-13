package cams.ui;

import cams.MainApp;
import cams.object.appitem.Camp;
import cams.object.person.*;
import cams.util.ScannerHelper;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;


import cams.util.ScannerHelper;


public class StudentMenuUI extends BaseUI{
    //private Student currentStudent;
    private Scanner input = ScannerHelper.getScannerInput();

    //public StudentMenuUI (Student student){
    //this.currentStudent = student;
    //}

    @Override
    protected int generateMenuScreen() {
        if (((Student)MainApp.currentUser).isCampCommittee()) return -1;
        printHeader("Student Main Menu");
        System.out.println("1) Go To Camp Menu");
        System.out.println("2) Go to Enquiries Menu");
        System.out.println("3) Change Password"); 
        System.out.println("4) Log out");
        System.out.println("0) Exit Application");
        printBreaks();

        int choice = doMenuChoice(5, 0);
        switch(choice){
            case 1:
                if(new StudentCampMenuUI().startMainMenu()) return 1; //dont noob make new menus
                break;
            case 2:
                if(new StudentEnquiryMenuUI().startMainMenu()) return 1;
                break;
            case 3:
                studentChangePassword();
                break;
            case 4:
                System.out.println("You have successfully logged out.");
                return -1;
            case 0:
                System.out.println("Closing application...");
                return 1; //shutdown
            default:
                throw new MenuChoiceInvalidException("Student Main Menu");
            }
        
        return 0;
    }
    

    private void studentChangePassword() {
        printBreaks();
        System.out.println("Enter your new password: ");
        MainApp.currentUser.changePassword(ScannerHelper.getNewPassword());
    }
}
