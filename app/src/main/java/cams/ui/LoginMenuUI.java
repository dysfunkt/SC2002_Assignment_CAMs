package cams.ui;

import cams.MainApp;
import cams.util.ScannerHelper;
import cams.object.person.*;
import cams.ui.StudentMenuUI;

import java.util.Scanner;

public class LoginMenuUI extends BaseUI{

    private Scanner input = ScannerHelper.getScannerInput();
    public Staff currentStaff;
    public Student currentStudent;
    
    protected int generateMenuScreen() {
        printHeader("Login Menu");
        System.out.println("1) Staff Login");
        System.out.println("2) Student Login");
        System.out.println("0) Exit");
        printBreaks();

        int choice = doMenuChoice(2, 0);
        switch (choice) {
            case 1:
                StaffLogin(); 
                return 1;
            case 2:
                StudentLogin();
                return 1;
            case 0:
                return 1; //shutdown
            default:
                throw new MenuChoiceInvalidException("Login Menu");
        }
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
                    currentStaff = loginStaff;
                    loginSuccess = true;
                    System.out.println("Successfully logged in as " + currentStaff.getUserID());
                    if (loginStaff.isFirstLogin()) firstTimeLoginChangePassword(loginStaff);
                    //call staff menu
                    System.out.println("Development: call staff menu here");
                } else {
                    System.out.println("Wrong Password!");
                }
            }
        } while (!loginSuccess);
    }

    private void StudentLogin() {
        Boolean loginSuccess = false;
        input.nextLine();
        do {
            System.out.print("Enter Username: ");
            String usernameInput = input.nextLine();
            System.out.print("Enter Password: ");
            String passwordInput = input.nextLine();

            Student loginStudent = null;

            for (int i = 0; i < MainApp.students.size(); i++) {
                if (MainApp.students.get(i).getUserID().equals(usernameInput)) {
                    loginStudent = MainApp.students.get(i);
                }
            }
            if (loginStudent == null) {
                System.out.println("Username does not exist!");
            } else {
                if (loginStudent.checkPassword(passwordInput)) {
                    currentStudent = loginStudent;
                    loginSuccess = true;
                    System.out.println("Successfully logged in as " + currentStudent.getUserID());
                    if (loginStudent.isFirstLogin()) firstTimeLoginChangePassword(loginStudent);
                    if (currentStudent.isCampCommittee()) {
                        //call camp committee menu here
                        System.out.println("Development: call camp committee menu here");
                    } else {
                        //call student menu here
                        //new StudentMenuUI().generateMenuScreen();
                        //System.out.println("Development: call student menu here");
                    }
                } else {
                    System.out.println("Wrong Password!");
                }
            }
        } while (!loginSuccess);
    }

    private void firstTimeLoginChangePassword(User s) {
        printBreaks();
        System.out.println("First time login. Please change password.");
        s.changePassword(ScannerHelper.getNewPassword());
    }
}
