package cams.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Scanner;

import cams.MainApp;
import cams.object.appitem.*;
import cams.object.person.*;
import cams.util.CSVStringHelper;
import cams.util.IDHelper;
import cams.util.ScannerHelper;

public class CampCommitteeMenuUI extends BaseUI {

    private Scanner input = ScannerHelper.getScannerInput();

    protected int generateMenuScreen() {
        printHeader("Camp Committee Main Menu");
        System.out.println("1) Go To Camp Menu");
        System.out.println("2) Go to Enquiries Menu");
        System.out.println("3) Go to Committee Actions Menu");
        System.out.println("4) Change Password");
        System.out.println("5) Log out");
        System.out.println("0) Exit Application");
        printBreaks();

        int choice = doMenuChoice(7, 0);
        switch (choice) {
            case 1:
                if (new CampCommitteeCampMenuUI().startMainMenu()) return 1;
                break;
            case 2:
                if (new CampCommitteeEnquiryMenuUI().startMainMenu()) return 1;
                break;
            case 3:
                if (new CampCommitteeActionsMenuUI().startMainMenu()) return 1;
                break;
            case 4:
                changePassword();
                break;
            case 5:
                System.out.println("You have successfully logged out.");
                return -1;
            case 0:
                System.out.println("Closing application...");
                return 1; //shutdown

            default:
                throw new MenuChoiceInvalidException("Camp Committee Main Menu");
        }
        return 0;
    }

    private void changePassword() {
        printBreaks();
        System.out.println("Enter your new password: ");
        MainApp.currentUser.changePassword(ScannerHelper.getNewPassword());
    }
}
