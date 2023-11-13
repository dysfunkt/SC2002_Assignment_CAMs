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

public class CampCommitteeEnquiryMenuUI extends BaseUI{
    private Scanner input = ScannerHelper.getScannerInput();

    protected int generateMenuScreen() {
        printHeader("Camp Committee Enquiry Menu");
        System.out.println("1) Submit Enquiries");
        System.out.println("2) View Enquiries");
        System.out.println("3) Return to Camp Committee Menu");
        System.out.println("0) Exit Application");
        printBreaks();
        int choice = doMenuChoice(3,0);
        switch (choice) {
            case 1:
                submitEnquiry(); //only for Camp Committee's camps!!
                break;
            case 2:
                viewEnquiries();
                break;
            case 3:
                System.out.println("Switching back to Camp Committee Menu.");
                return -1;
            case 0:
                System.out.println("Closing application...");
                return 1; //shutdown
            default:
                throw new MenuChoiceInvalidException("Camp Committee Enquiry Menu");
        }
        return 0;
    }


    public void submitEnquiry(){

    }


    public void viewEnquiries(){
        //iterate through all camps and check if this person is committing for the camp

        //ID of the camp the student is commitiing for
    }
}
