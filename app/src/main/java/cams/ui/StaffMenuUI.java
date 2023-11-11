package cams.ui;

import cams.MainApp;
import cams.object.appitem.Camp;
import cams.object.person.Staff;
import cams.util.ScannerHelper;

import java.util.ArrayList;
import java.util.Scanner;


public class StaffMenuUI extends BaseUI {
    
    private Scanner input = ScannerHelper.getScannerInput();

    protected int generateMenuScreen() {
        printHeader("Login Menu");
        System.out.println("1) Go To Camp Menu");
        System.out.println("2) Go to Enquiries Menu");
        System.out.println("3) Go to Suggestions Menu");
        System.out.println("4) Change Password");
        System.out.println("5) Log out");
        System.out.println("0) Exit Application");
        printBreaks();
        int choice = doMenuChoice(10, 0);
        switch (choice) {
            case 1:
                if(new StaffCampMenuUI().startMainMenu()) return 1;
                break;
            case 2:
                if(new StaffEnquiryMenuUI().startMainMenu()) return 1;
                break;
            case 3:
                if(new StaffSuggestionsMenuUI().startMainMenu()) return 1;
                break;
            case 4:
                ChangePassword();
                break;
            case 5:
                System.out.println("You have successfully logged out.");
                return -1;
            case 0:
                System.out.println("Closing application...");
                return 1; //shutdown
            default:
                throw new MenuChoiceInvalidException("Login Menu");
        }
        return 0;
    }

    public void ChangePassword(){}
}
