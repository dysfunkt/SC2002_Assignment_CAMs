package cams.boundary;

import cams.MainApp;
import cams.model.appitem.Camp;
import cams.model.appitem.Enquiry;
import cams.model.person.Student;
import cams.model.person.eFaculty;
import cams.util.ScannerHelper;
import java.util.Scanner;

public class CCEnquiryMenuUI extends BaseUI{
    private Scanner input = ScannerHelper.getScannerInput();

    protected int generateMenuScreen() {
        printHeader("Camp Committee Enquiry Menu");
        System.out.println("1) Submit An Enquiry");
        System.out.println("2) View Enquiries");
        System.out.println("3) Delete An Enquiry");
        System.out.println("4) Return to Camp Committee Menu");
        System.out.println("0) Exit Application");
        printBreaks();
        int choice = doMenuChoice(4,0);
        switch (choice) {
            case 1:
                submitEnquiry(); 
                break;
            case 2:
                viewEnquiries();
                break;
            case 3:
                deleteEnquiry();
                break;
            case 4:
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


    private void submitEnquiry() {
        printHeader("Submit Enquiry");
    
        // Get current student
        Student currentStudent = (Student) MainApp.currentUser;
    
        // Display the list of camps that the student can view
        System.out.println("Select the Camp ID to submit the enquiry:");
    
        for (Camp camp : MainApp.camps) {
            if (camp.getUserGroup().equals(MainApp.currentUser.getFaculty()) || camp.getUserGroup().equals(Enum.valueOf(eFaculty.class, "NTU")) && camp.isVisibility()) {
                System.out.println("Camp ID: " + camp.getID() + ", Camp Name: " + camp.getCampName());
            }
        }
    
        // Get the selected camp ID from the user
        String selectedCampID = ScannerHelper.getIntegerInput("Enter the Camp ID: ")+"";
    
        // Check if the selected camp ID is valid
        boolean validCampID = false;
        for (Camp camp : MainApp.camps) {
            if (camp.getID().equals(selectedCampID) && (camp.getUserGroup().equals(MainApp.currentUser.getFaculty()) || camp.getUserGroup().equals(Enum.valueOf(eFaculty.class, "NTU"))) && camp.isVisibility()) {
                validCampID = true;
                break;
            }
        }
    
        if (validCampID) {
            System.out.print("Enter your enquiry message: ");
            String enquiryMessage = input.nextLine();
    
            // Increment the enquiryID using UniqueID class
            String newEnquiryID = MainApp.uniqueID.getNextEnquiryID();
            MainApp.uniqueID.incrementEnquiryID();
    
            // Create a new Enquiry object
            Enquiry newEnquiry = new Enquiry(newEnquiryID, selectedCampID, currentStudent.getID(), enquiryMessage);
    
            // Add the new enquiry to the list of enquiries
            MainApp.enquiries.add(newEnquiry);
    
            // Print out the new enquiryID
            System.out.println("Enquiry submitted successfully. Your Enquiry ID is: " + newEnquiryID);
            printBreaks();
        } else {
            System.out.println("Invalid Camp ID. You don't have permission to submit an enquiry for the selected camp.");
        }
    }

    

    private void viewEnquiries() {
        printHeader("View Enquiries");
    
        // Get current student
        Student currentStudent = (Student) MainApp.currentUser;
    
        // Display the enquiries submitted by the current student
        int index = 1;
        for (Enquiry enquiry : MainApp.enquiries) {
            if (enquiry.getCreatedBy().equals(currentStudent.getID())) {
                System.out.println("Enquiry ID: " + enquiry.getID());
                System.out.println("Camp ID: " + enquiry.getCampID());
                System.out.println("Enquiry Message: " + enquiry.getEnquiryMessage());
                System.out.println("Processed: " + enquiry.isProcessed());
                System.out.println("Reply Viewed: " + enquiry.isReplyViewed());
                System.out.println("Reply Message: " + enquiry.viewReply());
                printBreaks();
                index++;
            }
        }
    
        // Check if there are no enquiries
        if (index == 1) {
            System.out.println("You haven't submitted any enquiries.");
        }
    
        printBreaks();
        } 

        
    private void deleteEnquiry() {
        // Get current student
        Student currentStudent = (Student) MainApp.currentUser;

        // Display the enquiries submitted by the current student
        int index = 1;
        for (Enquiry enquiry : MainApp.enquiries) {
            if (enquiry.getCreatedBy().equals(currentStudent.getID())) {
                System.out.println("Enquiry ID: " + enquiry.getID());
                System.out.println("Camp ID: " + enquiry.getCampID());
                System.out.println("Enquiry Message: " + enquiry.getEnquiryMessage());
                System.out.println("Processed: " + enquiry.isProcessed());
                System.out.println("Reply Viewed: " + enquiry.isReplyViewed());
                printBreaks();
                index++;
            }
        }

        // Check if there are no enquiries
        if (index == 1) {
            System.out.println("You haven't submitted any enquiries.");
            return;
        }

        // Get the Enquiry ID to delete from the user
        String enquiryToDelete = ScannerHelper.getIntegerInput("Enter the Enquiry ID to delete (0 to cancel): ")+"";

        if (enquiryToDelete.equals("0")) {
            System.out.println("Deletion canceled.");
            return;
        }

        // Find the Enquiry using the ID
        Enquiry enquiryToDeleteObject = null;
        for (Enquiry enquiry : MainApp.enquiries) {
            if (enquiry.getID() == enquiryToDelete && enquiry.getCreatedBy().equals(currentStudent.getID())) {
                enquiryToDeleteObject = enquiry;
                break;
            }
        }

        // Check if the Enquiry was found
        if (enquiryToDeleteObject != null) {
            // Remove the Enquiry from the list
            MainApp.enquiries.remove(enquiryToDeleteObject);
            System.out.println("Enquiry deleted successfully.");
        } else {
            System.out.println("Invalid Enquiry ID or you don't have permission to delete this enquiry.");
        }
        printBreaks();
    }
}
