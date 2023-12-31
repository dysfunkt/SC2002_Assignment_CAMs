package cams.boundary.campcommittee;

import cams.boundary.BaseUI;
import cams.boundary.login.ChangePasswordUI;
import cams.util.exception.MenuChoiceInvalidException;

/**
 * This class provides the main menu UI for camp committee members.
 * This class extends the BaseUI class.
 */
public class CCMenuUI extends BaseUI {

    /**
     * Displays the UI for camp committee member to access further menus to manage camps, manage enquiries, perform committee specific actions or to change their password.
     * @return -1 to return to the previous menu, 1 to exit the application, otherwise 0.
     */
    protected int generateMenuScreen() {
        printHeader("Camp Committee Main Menu");
        System.out.println("1) Go To Camp Menu");
        System.out.println("2) Go To Enquiry Menu");
        System.out.println("3) Go To Committee Actions Menu");
        System.out.println("4) Change Password");
        System.out.println("5) Log out");
        System.out.println("0) Exit Application");
        printBreaks();
        int choice = doMenuChoice(5, 0);
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
                new ChangePasswordUI().startMainMenu();
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

}
