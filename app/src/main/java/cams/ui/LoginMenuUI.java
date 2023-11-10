package cams.ui;

import cams.MainApp;
import cams.util.ScannerHelper;
import cams.object.person.*;
import cams.ui.StudentMenuUI;

import java.util.Scanner;

public class LoginMenuUI extends BaseUI{

    private Scanner input = ScannerHelper.getScannerInput();
    
    
    protected int generateMenuScreen() {
        printHeader("Login Menu");
        System.out.println("1) Staff Login");
        System.out.println("2) Student Login");
        System.out.println("0) Exit Application");
        printBreaks();

        int choice = doMenuChoice(2, 0);
        switch (choice) {
            case 1:
                StaffLogin(); 
                if(new StaffMenuUI().startMainMenu()) return 1;
                break;
            case 2:
                if (StudentLogin()){
                    //call camp committee menu here
                    System.out.println("Development: call CC menu here");
                } else {
                    //call student menu here
                    System.out.println("Development: call student menu here");
                    //if (new StudentMenuUI().generateMenuScreen()) return 1;
                }
                break;
            case 0:
                System.out.println("Closing application...");
                return 1; //shutdown
            default:
                throw new MenuChoiceInvalidException("Login Menu");
        }
        return 0;
    }

    private void StaffLogin() {
        Boolean loginSuccess = false;
        input.nextLine();
        do {
            System.out.print("Enter Username: ");
            String usernameInput = input.nextLine();
            System.out.print("Enter Password: ");
            String passwordInput = input.nextLine();

            Staff loginStaff = null;

            for (int i = 0; i < MainApp.staffs.size(); i++) {
                if (MainApp.staffs.get(i).getUserID().equals(usernameInput)) {
                    loginStaff = MainApp.staffs.get(i);
                }
            }
            if (loginStaff == null) {
                System.out.println("Username does not exist!");
            } else {
                if (loginStaff.checkPassword(passwordInput)) {
                    MainApp.currentUser = loginStaff;
                    loginSuccess = true;
                    System.out.println("Successfully logged in as " + MainApp.currentUser.getUserID());
                    if (loginStaff.isFirstLogin()) firstTimeLoginChangePassword(loginStaff);
                    //finish login sequence                    
                } else {
                    System.out.println("Wrong Password!");
                }
            }
        } while (!loginSuccess);
    }

    private Boolean StudentLogin() {
        Boolean loginSuccess = false;
        Student loginStudent = null;
        input.nextLine();
        do {
            System.out.print("Enter Username: ");
            String usernameInput = input.nextLine();
            System.out.print("Enter Password: ");
            String passwordInput = input.nextLine();

            

            for (int i = 0; i < MainApp.students.size(); i++) {
                if (MainApp.students.get(i).getUserID().equals(usernameInput)) {
                    loginStudent = MainApp.students.get(i);
                }
            }
            if (loginStudent == null) {
                System.out.println("Username does not exist!");
            } else {
                if (loginStudent.checkPassword(passwordInput)) {
                    MainApp.currentUser = loginStudent;
                    loginSuccess = true;
                    System.out.println("Successfully logged in as " + MainApp.currentUser.getUserID());
                } else {
                    System.out.println("Wrong Password!");
                }
            }
        } while (!loginSuccess);
        if (loginStudent.isFirstLogin()) firstTimeLoginChangePassword(loginStudent);
        if (loginStudent.isCampCommittee()) {
            return true;
        } else {
            return false;
        }
    }

    private void firstTimeLoginChangePassword(User s) {
        printBreaks();
        System.out.println("First time login. Please change password.");
        s.changePassword(ScannerHelper.getNewPassword());
    }
}
