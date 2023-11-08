package cams.ui;

import cams.ui.LoginMenuUI;
import cams.util.ScannerHelper;
import cams.object.person.*;

public class StudentMenuUI extends BaseUI{

    @Override
    protected int generateMenuScreen() {
        printHeader("Student Menu");
        System.out.println("1) View all camps"); //print list of camps and their remaining slots
        System.out.println("2) View registered camps");
        System.out.println("3) Register for a camp");
        System.out.println("4) Withdraw from a camp");
        System.out.println("5) Submit an enquiry");
        System.out.println("6) View your enquiries"); 
        System.out.println("7) Exit Student Menu");

        return 1;
    }


    private void studentChangePassword(User s) {
        printBreaks();
        System.out.println("Enter your new password: ");
        s.changePassword(ScannerHelper.getNewPassword());
    }


}






//using LoginMenuUI.current
//