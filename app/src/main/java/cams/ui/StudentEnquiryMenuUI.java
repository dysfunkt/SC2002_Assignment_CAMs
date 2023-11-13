package cams.ui;

import cams.MainApp;
import cams.object.appitem.Enquiry;
import cams.object.person.Student;
import cams.util.ScannerHelper;
import java.util.Scanner;

public class StudentEnquiryMenuUI extends BaseUI{
    @Override
    protected int generateMenuScreen() {
        printHeader("Manage Enquiries");
        System.out.println("1) Submit Enquiries");
        System.out.println("2) View Enquiries");
        System.out.println("3) Delete Enquiries");
        System.out.println("3) Back to Student Menu");
        printBreaks();

        int choice = doMenuChoice(3, 0);

        switch (choice) {
            case 1:
                studentSubmitEnquiry();
                break;
            case 2:
                studentViewEnquiries();
                break;
            case 3:
                studentdeleteEnquiries();
                break;
            case 4:
                System.out.println("Switching back to Student Menu.");
                return -1;
            default:
                throw new MenuChoiceInvalidException("Student Enquiries Menu");
        }
        return 0;
    }

    private void studentSubmitEnquiry() {
        printHeader("Submit Enquiry");
    
        // Get the current student
        Student currentStudent = (Student) MainApp.currentUser;
    
        // Use the standard Scanner class to get the enquiry message from the user
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your enquiry message: ");
        String enquiryMessage = scanner.nextLine();
    
        // Increment the enquiryID using UniqueID class
        int newEnquiryID = MainApp.uniqueID.getNextEnquiryID();
        MainApp.uniqueID.incrementEnquiryID();
    
        // Create a new Enquiry object
        Enquiry newEnquiry = new Enquiry(newEnquiryID, 0, currentStudent.getUserID(), enquiryMessage);
    
        // Add the new enquiry to the list of enquiries
        MainApp.enquiries.add(newEnquiry);
    
        // Print out the new enquiryID
        System.out.println("Enquiry submitted successfully. Your Enquiry ID is: " + newEnquiryID);
        printBreaks();
    }
    


    private void studentViewEnquiries() {
        printHeader("View Enquiries");
    
        // Get the current student
        Student currentStudent = (Student) MainApp.currentUser;
    
        // Display the enquiries submitted by the current student
        int index = 1;
        for (Enquiry enquiry : MainApp.enquiries) {
            if (enquiry.getCreatedBy().equals(currentStudent.getUserID())) {
                System.out.println("Enquiry ID: " + enquiry.getEnquiryID());
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
        }
    
        printBreaks();
        } 

    private void studentdeleteEnquiries() {
    // Get the current student
    Student currentStudent = (Student) MainApp.currentUser;

    // Display the enquiries submitted by the current student
    int index = 1;
    for (Enquiry enquiry : MainApp.enquiries) {
        if (enquiry.getCreatedBy().equals(currentStudent.getUserID())) {
            System.out.println("Enquiry ID: " + enquiry.getEnquiryID());
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
    int enquiryToDelete = ScannerHelper.getIntegerInput("Enter the Enquiry ID to delete (0 to cancel): ");

    if (enquiryToDelete == 0) {
        System.out.println("Deletion canceled.");
        return;
    }

    // Find the Enquiry using the ID
    Enquiry enquiryToDeleteObject = null;
    for (Enquiry enquiry : MainApp.enquiries) {
        if (enquiry.getEnquiryID() == enquiryToDelete && enquiry.getCreatedBy().equals(currentStudent.getUserID())) {
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
