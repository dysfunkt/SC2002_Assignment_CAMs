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

  
    public void ViewAllEnquiries() {
        printHeader("View All Enquiries");
        // Assuming 'enquiries' is a list containing all Enquiry objects
        for (Enquiry enquiry : enquiries) {
            // Display relevant information about each enquiry
            if (staff.getCampsInCharge().stream().anyMatch(camp -> camp.getCampID() == enquiry.getCampID())){
                System.out.println("Enquiry ID: " + enquiry.getEnquiryID());
                System.out.println("Camp ID: " + enquiry.getCampID());
                System.out.println("Created By: " + enquiry.getCreatedBy());
                System.out.println("Enquiry Message: " + enquiry.getEnquiryMessage());
                System.out.println("Processed: " + enquiry.isProcessed());
                System.out.println("Deleted: " + enquiry.isDeleted());
                System.out.println("Reply Viewed: " + enquiry.isReplyViewed());
                System.out.println("-----------------------------");
            }
        }
        printBreaks();
    }
    public void ReplyEnquiry() {
        printHeader("Reply to Enquiry");
        System.out.print("Enter Enquiry ID to reply: ");
        int enquiryIDToReply = input.nextInt();
        input.nextLine(); // Consume the newline character
    
        // Find the Enquiry with the given ID
        Enquiry selectedEnquiry = null;
        for (Enquiry enquiry : enquiries) {
            if (enquiry.getEnquiryID() == enquiryIDToReply) {
                selectedEnquiry = enquiry;
                break;
            }
        }
    
        // Check if the enquiry was found
        if (selectedEnquiry != null) {
            if (!selectedEnquiry.isProcessed()) {
                System.out.print("Enter your reply: ");
                String replyMessage = input.nextLine();
    
                // Set the reply and mark the enquiry as processed
                selectedEnquiry.reply(replyMessage);
                System.out.println("Reply sent successfully!");
            } else {
                System.out.println("Enquiry with ID " + enquiryIDToReply + " has already been processed.");
            }
        } else {
            System.out.println("Enquiry with ID " + enquiryIDToReply + " not found.");
        }
    
        printBreaks();
    }
}
