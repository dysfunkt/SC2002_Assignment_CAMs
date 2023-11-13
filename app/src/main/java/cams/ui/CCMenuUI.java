package cams.ui;

import cams.MainApp;
import cams.util.ScannerHelper;

public class CCMenuUI extends BaseUI {

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
                if (new CCCampMenuUI().startMainMenu()) return 1;
                break;
            case 2:
                if (new CCEnquiryMenuUI().startMainMenu()) return 1;
                break;
            case 3:
                if (new CCActionsMenuUI().startMainMenu()) return 1;
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
