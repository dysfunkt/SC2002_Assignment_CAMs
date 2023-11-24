package cams.boundary;

import cams.MainApp;
import cams.controller.account.AccountManager;
import cams.controller.account.user.CurrentUser;
import cams.model.person.*;
import cams.util.ScannerHelper;

public class StudentMenuUI extends BaseUI{
    protected int generateMenuScreen() {
        if (AccountManager.checkCC(CurrentUser.get())) return -1;
        printHeader("Student Main Menu");
        System.out.println("1) Go To Camp Menu");
        System.out.println("2) Go To Enquiry Menu");
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
                new ChangePasswordUI().startMainMenu();
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
    

    
}
