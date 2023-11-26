package cams.boundary.student;

import cams.boundary.BaseUI;
import cams.boundary.modeldisplayer.ModelDisplayer;
import cams.controller.camp.CampManager;
import cams.controller.enquiry.EnquiryManager;
import cams.model.appitem.Enquiry;
import cams.model.camp.Camp;
import cams.util.exception.AlreadyProcessedException;
import cams.util.exception.MenuChoiceInvalidException;
import cams.util.exception.ModelAlreadyExistsException;
import cams.util.exception.ModelNotFoundException;
import cams.util.exception.OperationCancelledException;
import cams.util.id.IDHelper;
import cams.util.ui.ScannerHelper;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * This class provides a UI for student to manage enquiries.
 * This class extends the BaseUI class.
 */
public class StudentEnquiryMenuUI extends BaseUI{
    private Scanner input = ScannerHelper.getScannerInput();
    
    
    /**
     * Displays the UI for student to manage enquiries.
     * @return -1 to return to the previous menu, 1 to exit the application, otherwise 0.
     */
    protected int generateMenuScreen() {
        printHeader("Student Enquiry Menu");
        System.out.println("1) Submit an Enquiry");
        System.out.println("2) View Your Enquiries");
        System.out.println("3) Edit an Enquiry");
        System.out.println("4) Delete an Enquiry");
        System.out.println("5) Return to Student Menu");
        System.out.println("0) Exit Application");
        printBreaks();
        int choice = doMenuChoice(5, 0);
        switch (choice) {
            case 1:
                submitEnquiry();
                break;
            case 2:
                viewEnquiries();
                break;
            case 3:
                editEnquiry();
                break;
            case 4:
                deleteEnquiry();
                break;
            case 5:
                System.out.println("Switching back to Student Menu.");
                return -1;
            case 0:
                System.out.println("Closing application...");
                return 1; //shutdown
            default:
                throw new MenuChoiceInvalidException("Student Enquiry Menu");
        }
        return 0;
    }


    /**
     * Allows student to perform edits on an existing enquiry they created.
     */
    private void editEnquiry() {
        printHeader("Edit Enquiries");
        List<Enquiry> enquiryList = EnquiryManager.getUserCreatedList();
        if (enquiryList.size() == 0) {
            System.out.println("No Enquiries Available! Returning to Enquiry Menu...");
            return;
        }
        Collections.sort(enquiryList, Comparator.comparing(Enquiry::getID));
        ModelDisplayer.displayListOfDisplayable(enquiryList);
        printBreaks();
        String enquiryID = ScannerHelper.getIDInput("Enter ID of enquiry to edit (Enter 0 to cancel): ", IDHelper.extractEnquiryIDs(enquiryList), "Enter one of the IDs!");
        if (enquiryID.equals("0")){
            System.out.println("Cancelling edit. Returning to Enquiry Menu...");
            return;
        }
        System.out.print("Enter your enquiry message: ");
        String enquiryMessage = input.nextLine();
        try {
            EnquiryManager.editEnquiry(enquiryID, enquiryMessage);
        } catch (AlreadyProcessedException e) {
            System.out.println("Cancelling edit. Returning to Enquiry Menu...");
            return;
        } catch (ModelNotFoundException e) {
            System.out.println(e.getLocalizedMessage());
            return;
        }
        System.out.println("Enquiry successfully edited.");
    }


    /**
     * Submits an enquiry by prompting the student to select a camp and enter an enquiry message.
     */
    private void submitEnquiry() {
        printHeader("Submit Enquiry");
        List<Camp> campList = CampManager.getListByFilter();
        if (campList.size() == 0) {
            System.out.println("No Camps Available! Returning to Enquiry Menu...");
            return;
        }
        Collections.sort(campList, Comparator.comparing(Camp::getCampName));
        ModelDisplayer.displayListOfDisplayable(campList);
        printBreaks();
        String campID = ScannerHelper.getIDInput("Enter ID of camp to submit request to (Enter 0 to cancel): ", IDHelper.extractCampIDs(campList), "Enter one of the IDs!");
        if (campID.equals("0")) {
            System.out.println("Cancelling sumbission. Returning to Enquiry Menu...");
            return;
        }
        System.out.print("Enter your enquiry message: ");
        String enquiryMessage = input.nextLine();
        try{
            EnquiryManager.createEnquiry(campID, enquiryMessage);
        } catch (ModelAlreadyExistsException e) {
            System.out.println(e.getLocalizedMessage());
            return;
        }
        System.out.println("Enquiry successfully submitted.");
    }

    /**
     * Allow student to view enquiries they created.
     */
    private void viewEnquiries() {
        printHeader("View Enquiries");
        List<Enquiry> enquiryList = EnquiryManager.getUserCreatedList();
        if (enquiryList.size() == 0) {
            System.out.println("No Enquiries Available! Returning to Enquiry Menu...");
            return;
        }
        Collections.sort(enquiryList, Comparator.comparing(Enquiry::getID));
        ModelDisplayer.displayListOfDisplayable(enquiryList);
    }

    /**
     * Deletes an enquiry created by the student.
     */
    private void deleteEnquiry() {
        printHeader("Delete Enquiries");
        List<Enquiry> enquiryList = EnquiryManager.getUserCreatedList();
        if (enquiryList.size() == 0) {
            System.out.println("No Enquiries Available! Returning to Enquiry Menu...");
            return;
        }
        Collections.sort(enquiryList, Comparator.comparing(Enquiry::getID));
        ModelDisplayer.displayListOfDisplayable(enquiryList);
        printBreaks();
        String enquiryID = ScannerHelper.getIDInput("Enter ID of enquiry to delete (Enter 0 to cancel): ", IDHelper.extractEnquiryIDs(enquiryList), "Enter one of the IDs!");
        if (enquiryID.equals("0")) {
            System.out.println("Cancelling delete. Returning to Enquiry Menu...");
            return;
        }
        try {
            EnquiryManager.deleteEnquiry(enquiryID);
        } catch (AlreadyProcessedException e) {
            System.out.println("Cancelling delete. Returning to Enquiry Menu...");
            return;
        } catch (OperationCancelledException e) {
            System.out.println("Cancelling delete. Returning to Enquiry Menu...");
            return;
        } catch (ModelNotFoundException e ){
            System.out.println(e.getLocalizedMessage());
            return;
        }
        System.out.println("Enquiry successfull deleted.");
    }
}
