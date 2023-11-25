package cams.boundary.campcommittee;

import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.List;

import cams.boundary.BaseUI;
import cams.boundary.modeldisplayer.ModelDisplayer;
import cams.controller.account.user.CurrentUser;
import cams.controller.enquiry.EnquiryManager;
import cams.controller.suggestion.SuggestionManager;
import cams.model.appitem.*;
import cams.model.camp.Camp;
import cams.model.person.*;
import cams.repository.appitem.CampRepository;
import cams.util.exception.AlreadyProcessedException;
import cams.util.exception.MenuChoiceInvalidException;
import cams.util.exception.ModelAlreadyExistsException;
import cams.util.exception.ModelNotFoundException;
import cams.util.exception.OperationCancelledException;
import cams.util.id.IDHelper;
import cams.util.ui.ScannerHelper;

public class CCActionsMenuUI extends BaseUI{
    private Scanner input = ScannerHelper.getScannerInput();

    
    /** 
     * @return int
     */
    protected int generateMenuScreen() {
        printHeader("Camp Committee Actions Menu");
        System.out.println("1) View Registered Camp Details"); //Details of the camp they have registered for
        System.out.println("2) View Enquiries"); 
        System.out.println("3) Reply To An Enquiry");
        System.out.println("4) Submit a Suggestion");
        System.out.println("5) View My Suggestions");
        System.out.println("6) Edit a Suggestion");
        System.out.println("7) Delete a Suggestion");
        System.out.println("8) Return to Camp Committee Menu");
        System.out.println("0) Exit Application");
        printBreaks();
        int choice = doMenuChoice(8,0);
        switch (choice) {
            case 1:
                viewCampDetails();
                break;
            case 2:
                viewEnquiries();
                break;
            case 3:
                replyEnquiry();
                break;
            case 4:
                submitSuggestion();
                break;
            case 5:
                viewMySuggestions();
                break;
            case 6:
                editSuggestion();
                break;
            case 7:
                deleteSuggestion();
                break;
            case 8:
                System.out.println("Switching back to Camp Committee Menu.");
                return -1;
            case 0:
                System.out.println("Closing application...");
                return 1; //shutdown
            default:
                throw new MenuChoiceInvalidException("Camp Committee Actions Menu");
        }
        return 0;
    }
    
    private void viewCampDetails() {
        printHeader("Camp Details");
        Camp camp = null;
        try {
            camp = CampRepository.getInstance().getByID(((Student)CurrentUser.get()).getCampIDCommittingFor());
        } catch (ModelNotFoundException e) {
            System.out.println(e.getLocalizedMessage());
        }
        ModelDisplayer.displaySingleDisplayable(camp);
    }

    public void viewEnquiries() {
        printHeader("View Enquiries");
        List<Enquiry> enquiryList = EnquiryManager.getListByCampID(((Student)CurrentUser.get()).getCampIDCommittingFor());
        Collections.sort(enquiryList, Comparator.comparing(Enquiry::getID));
        ModelDisplayer.displayListOfDisplayable(enquiryList);
    }

    public void replyEnquiry() {
        printHeader("Reply to An Enquiry");
        List<Enquiry> enquiryList = EnquiryManager.getListByCampIDToReply(((Student)CurrentUser.get()).getCampIDCommittingFor());
            if (enquiryList.size() == 0) {
            System.out.println("No Enquiries Available! Returning to Camp Committee Actions Menu...");
            return;
        }
        String enquiryIDToReply = ScannerHelper.getIDInput("Enter ID of enquiry to reply (Enter 0 to cancel): ", IDHelper.extractEnquiryIDs(enquiryList), "Enter one of the IDs!");
        if (enquiryIDToReply.equals("0")) {
            System.out.println("Cancelling reply. Returning to Camp Committee Actions Menu...");
            return;
        }
        System.out.print("Enter your reply: ");
        String replyMessage = input.nextLine();
        try{
            EnquiryManager.replyToEnquiry(enquiryIDToReply, replyMessage);
        } catch (ModelNotFoundException e) {
            System.out.println(e.getLocalizedMessage());
            return;
        }
        System.out.println("Reply sent successfully!");
        
    }

    public void submitSuggestion() {
        printHeader("Submit Suggestion");
        System.out.print("Enter your Suggestion: ");
        String message = input.nextLine();
        try {
            SuggestionManager.createSuggestion(message);
        } catch (ModelAlreadyExistsException e) {
            System.out.println(e.getLocalizedMessage());
        }
        System.out.println("Suggestion submitted successfully!");
    }

    public void viewMySuggestions() {
        printHeader("View My Suggestions");
        List<Suggestion> suggestionList = SuggestionManager.getListByUserID(CurrentUser.get().getID());
        if (suggestionList.size() == 0) {
            System.out.println("No Suggestions Available! Returning to Camp Committee Actions Menu...");
            return;
        }
        Collections.sort(suggestionList, Comparator.comparing(Suggestion::getID));
        ModelDisplayer.displayListOfDisplayable(suggestionList);
    }

    public void editSuggestion() {
        printHeader("Edit Suggestion");
        List<Suggestion> suggestionList = SuggestionManager.getListByUserID(CurrentUser.get().getID());
        if (suggestionList.size() == 0) {
            System.out.println("No Suggestions Available! Returning to Camp Committee Actions Menu...");
            return;
        }
        Collections.sort(suggestionList, Comparator.comparing(Suggestion::getID));
        ModelDisplayer.displayListOfDisplayable(suggestionList);
        printBreaks();
        String suggestionID = ScannerHelper.getIDInput("Enter ID of suggestion to edit (Enter 0 to cancel): ", IDHelper.extractSuggestionIDs(suggestionList), "Enter one of the IDs!");
        if (suggestionID.equals("0")) {
            System.out.println("Cancelling edit. Returning to Camp Committee Actions Menu...");
            return;
        }
        System.out.print("Enter new suggestion message: ");
        String message = input.nextLine();
        try{
            SuggestionManager.editSuggestion(suggestionID, message);
        } catch (AlreadyProcessedException e) {
            System.out.println("Cancelling edit. Returning to Camp Committee Actions Menu...");
            return;
        } catch (ModelNotFoundException e) {
            System.out.println(e.getLocalizedMessage());
            return;
        }
        System.out.println("Suggestion edited successfully.");
    
    }
    
    public void deleteSuggestion() {
        printHeader("Delete a Suggestion");
        List<Suggestion> suggestionList = SuggestionManager.getListByUserID(CurrentUser.get().getID());
        if (suggestionList.size() == 0) {
            System.out.println("No Suggestions Available! Returning to Camp Committee Actions Menu...");
            return;
        }
        Collections.sort(suggestionList, Comparator.comparing(Suggestion::getID));
        ModelDisplayer.displayListOfDisplayable(suggestionList);
        printBreaks();
        String suggestionID = ScannerHelper.getIDInput("Enter ID of suggestion to delete (Enter 0 to cancel): ", IDHelper.extractSuggestionIDs(suggestionList), "Enter one of the IDs!");
        if (suggestionID.equals("0")) {
            System.out.println("Cancelling delete. Returning to Camp Committee Actions Menu...");
            return;
        }
        try {
            SuggestionManager.deleteSuggestion(suggestionID);
        } catch (AlreadyProcessedException e) {
            System.out.println("Cancelling edit. Returning to Camp Committee Actions Menu...");
            return;
        } catch (OperationCancelledException e) {
            System.out.println("Cancelling edit. Returning to Camp Committee Actions Menu...");
            return;
        } catch (ModelNotFoundException e) {
            System.out.println(e.getLocalizedMessage());
            return;
        }
        System.out.println("Suggestion deleted successfully.");
    }
    
}
