package cams.boundary.staff;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import cams.boundary.BaseUI;
import cams.boundary.modeldisplayer.ModelDisplayer;
import cams.controller.account.user.CurrentUser;
import cams.controller.enquiry.EnquiryManager;
import cams.model.appitem.*;
import cams.model.person.*;
import cams.util.IDHelper;
import cams.util.exception.MenuChoiceInvalidException;
import cams.util.exception.ModelNotFoundException;
import cams.util.ui.ScannerHelper;

public class StaffEnquiryMenuUI extends BaseUI{
    private Scanner input = ScannerHelper.getScannerInput();

    protected int generateMenuScreen() {
        printHeader("Staff Enquiry Menu");
        System.out.println("1) View All Enquiries");
        System.out.println("2) Reply to an Enquiry");
        System.out.println("3) Return to Staff Menu");
        System.out.println("0) Exit Application");
        printBreaks();
        int choice = doMenuChoice(3, 0);
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


    public void viewAllEnquiries() {
        printHeader("View All Enquiries");
        List<Enquiry> list = EnquiryManager.getUnprocessedListByCampIDList(((Staff)CurrentUser.get()).getCampsInChargeID());
        Collections.sort(list, Comparator.comparing(Enquiry::getID));
        ModelDisplayer.displayListOfDisplayable(list);
    }


    public void replyEnquiry() {
        printHeader("Reply to Enquiry");
        List<Enquiry> enquiryList = EnquiryManager.getUnprocessedListByCampIDList(((Staff)CurrentUser.get()).getCampsInChargeID());
        Collections.sort(enquiryList, Comparator.comparing(Enquiry::getID));
        ModelDisplayer.displayListOfDisplayable(enquiryList);
    
        String enquiryID = ScannerHelper.getIDInput("Enter the ID of an enquiry to reply (Enter 0 to cancel): ", IDHelper.extractEnquiryIDs(enquiryList), "Enter one of the IDs!");
        if (enquiryID.equals("0")) {
            System.out.println("Cancelling reply. Returning to Enquiry Menu...");
            return;
        }

        System.out.print("Enter your reply: ");
        String replyMessage = input.nextLine();

        try {
            EnquiryManager.replyToEnquiry(enquiryID, replyMessage);
        } catch (ModelNotFoundException e) {
            System.out.println("Failed to reply. Returning to Enquiry Menu...");
        }
        System.out.println("Reply sent successfully!");
    }
}