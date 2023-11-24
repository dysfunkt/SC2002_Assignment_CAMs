package cams.boundary.login;

import cams.boundary.BaseUI;
import cams.boundary.campcommittee.CCMenuUI;
import cams.boundary.staff.StaffMenuUI;
import cams.boundary.student.StudentMenuUI;
import cams.controller.account.AccountManager;
import cams.controller.account.user.CurrentUser;
import cams.model.person.*;
import cams.util.exception.MenuChoiceInvalidException;
import cams.util.exception.ModelNotFoundException;
import cams.util.exception.PasswordIncorrectException;
import cams.util.ui.ScannerHelper;

import java.util.Scanner;

public class LoginMenuUI extends BaseUI{

    private Scanner input = ScannerHelper.getScannerInput();

    protected int generateMenuScreen() {
        printHeader("Login Menu");
        System.out.println("1) Login");
        System.out.println("0) Exit Application");
        printBreaks();
        int choice = doMenuChoice(1, 0);
        switch (choice) {
            case 1:
                if(login()) return 1;
                break;
            case 0:
                System.out.println("Closing application...");
                return 1; //shutdown
            default:
                throw new MenuChoiceInvalidException("Login Menu");
        }
        return 0;
    }

    private boolean login() {
        printBreaks();
        System.out.println("1) Staff");
        System.out.println("2) Student");
        printBreaks();
        int domain = ScannerHelper.getIntegerInput("Select your domain (1-2): ", 0,3);
        UserType userType = null;
        switch (domain) {
            case 1:
                userType = UserType.STAFF;
                break;
            case 2:
                userType = UserType.STUDENT;
                break;
            default:
                break;
        }
        System.out.print("Enter Username: ");
        String usernameInput = input.nextLine();
        System.out.print("Enter Password: ");
        String passwordInput = input.nextLine();
        try {
            User user = AccountManager.login(userType, usernameInput, passwordInput);
            switch (domain) {
                case 1:
                    if(new StaffMenuUI().startMainMenu()) return true;
                    break;
                case 2: 
                    if (AccountManager.checkCC(user)) {
                        if (new CCMenuUI().startMainMenu()) return true;
                    } else {
                        if (new StudentMenuUI().startMainMenu()) return true;
                    }
                    break;
            }
        } catch (PasswordIncorrectException e) {
            System.out.println("Incorrect Password.");
        } catch (ModelNotFoundException e) {
            System.out.println("Username not found.");
        }
        return false;
    }


}