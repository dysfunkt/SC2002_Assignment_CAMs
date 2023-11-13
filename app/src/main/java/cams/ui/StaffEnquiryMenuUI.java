package cams.ui;

import java.util.Scanner;

import cams.util.ScannerHelper;

public class StaffEnquiryMenuUI extends BaseUI{

    private Scanner input = ScannerHelper.getScannerInput();

    protected int generateMenuScreen() {
        printHeader("Staff Enquiry Menu");
        System.out.println("1) View All Enquiries");
        System.out.println("2) Reply to an Enquiry");
        System.out.println("3) Return to Staff Menu");
        System.out.println("0) Exit Application");
        printBreaks();
        int choice = doMenuChoice(10, 0);
        switch (choice) {
            case 1:
                viewAllEnquiries();
                break;
            case 2:
                replyEnquiry();
                break;
            case 3:
                System.out.println("Switching back to Staff Menu.");
                return -1;
            case 0:
                System.out.println("Closing application...");
                return 1; //shutdown
            default:
                throw new MenuChoiceInvalidException("Staff Enquiry Menu");
        }
        return 0;
    }


    public void viewAllEnquiries(){

    }


    public void replyEnquiry(){
        
    }
}
