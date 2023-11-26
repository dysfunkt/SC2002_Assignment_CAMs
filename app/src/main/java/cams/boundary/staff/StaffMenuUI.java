package cams.boundary.staff;

import cams.boundary.BaseUI;
import cams.boundary.login.ChangePasswordUI;
import cams.util.exception.MenuChoiceInvalidException;

/**
 * This class provides a main menu UI for staff
 */
public class StaffMenuUI extends BaseUI {

    
    /**
     * isplays the UI for staff to access further menus to manage camps, manage enquiries, manage suggestions or to change their password.
     * @return int -1 to return to the previous menu, 1 to exit the application, otherwise 0.
     */
    protected int generateMenuScreen() {
        printHeader("Staff Main Menu");
        System.out.println("1) Go to Camp Menu");
        System.out.println("2) Go to Enquiry Menu");
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
                new ChangePasswordUI().startMainMenu();
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


    
}
