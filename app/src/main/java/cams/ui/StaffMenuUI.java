package cams.ui;

import cams.MainApp;
import cams.util.ScannerHelper;

public class StaffMenuUI extends BaseUI {

    protected int generateMenuScreen() {
        printHeader("Staff Main Menu");
        System.out.println("1) Go To Camp Menu");
        System.out.println("2) Go to Enquiries Menu");
        System.out.println("3) Go to Suggestions Menu");
        System.out.println("4) Change Password");
        System.out.println("5) Log out");
        System.out.println("0) Exit Application");
        printBreaks();
        int choice = doMenuChoice(5, 0);
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
                changePassword();
                break;
            case 5:
                System.out.println("You have successfully logged out.");
                return -1;
            case 0:
                System.out.println("Closing application...");
                return 1; //shutdown
            default:
                throw new MenuChoiceInvalidException("Staff Main Menu");
        }
        return 0;
    }


    public void changePassword(){
        printBreaks();
        System.out.println("Enter your new password: ");
        MainApp.currentUser.changePassword(ScannerHelper.getNewPassword());
    }

    
}
