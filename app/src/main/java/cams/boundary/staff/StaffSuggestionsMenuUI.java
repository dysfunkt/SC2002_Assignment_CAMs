package cams.boundary.staff;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cams.boundary.BaseUI;
import cams.boundary.modeldisplayer.ModelDisplayer;
import cams.controller.account.user.CurrentUser;
import cams.controller.suggestion.SuggestionManager;
import cams.model.appitem.*;
import cams.model.person.Staff;
import cams.util.IDHelper;
import cams.util.exception.MenuChoiceInvalidException;
import cams.util.exception.ModelNotFoundException;
import cams.util.ui.ScannerHelper;

/**
 * This class provides a UI for staff to manage camp suggestions
 */
public class StaffSuggestionsMenuUI extends BaseUI{
    
    /** 
     * @return int
     */
    protected int generateMenuScreen() {
        printHeader("Staff Suggestions Menu");
        System.out.println("1) View All Suggestions");
        System.out.println("2) Approve a Suggestion");
        System.out.println("3) Reject a Suggestion");
        System.out.println("4) Return to Staff Menu");
        System.out.println("0) Exit Application");
        printBreaks();
        int choice = doMenuChoice(3, 0);
        switch (choice) {
            case 1:
                viewAllSuggestions();
                break;
            case 2:
                approveSuggestion();
                break;
            case 3:
                rejectSuggestion();
                break;
            case 4:
                System.out.println("Switching back to Staff Menu.");
                return -1;
            case 0:
                System.out.println("Closing application...");
                return 1; //shutdown
            default:
                throw new MenuChoiceInvalidException("Staff Suggestions Menu");
        }
        return 0;
    }


    /**
     * This method displays all unprocessed suggestions for the camps the staff member is in charge of.
     */
    public void viewAllSuggestions() {
        printHeader("View All Suggestions");
        List<Suggestion> list = SuggestionManager.getUnprocessedListByCampIDList(((Staff)CurrentUser.get()).getCampsInChargeID());
        Collections.sort(list, Comparator.comparing(Suggestion::getID));
        ModelDisplayer.displayListOfDisplayable(list);
    }

    /**
     * This method allows the staff to approve a suggestion by providing the suggestion ID.
     */
    public void approveSuggestion() {
        printHeader("Approve Suggestion");
        List<Suggestion> suggestionList = SuggestionManager.getUnprocessedListByCampIDList(((Staff)CurrentUser.get()).getCampsInChargeID());
        Collections.sort(suggestionList, Comparator.comparing(Suggestion::getID));
        ModelDisplayer.displayListOfDisplayable(suggestionList);

        String suggestionID = ScannerHelper.getIDInput("Enter the ID of an suggestion to approve (Enter 0 to cancel): ", IDHelper.extractSuggestionIDs(suggestionList), "Enter one of the IDs!");
        if (suggestionID.equals("0")) {
            System.out.println("Cancelling reply. Returning to Suggestion Menu...");
            return;
        }

        try {
            SuggestionManager.approveSuggestion(suggestionID);
        } catch (ModelNotFoundException e) {
            System.out.println("Failed to approve. Returning to Suggestion Menu...");
        }
        System.out.println("Suggestion approved successfully!");
    }

    /**
     * This method allows staff to reject a suggestion by providing the suggestion ID.
     */
    public void rejectSuggestion() {
        printHeader("Reject Suggestion");
        List<Suggestion> suggestionList = SuggestionManager.getUnprocessedListByCampIDList(((Staff)CurrentUser.get()).getCampsInChargeID());
        Collections.sort(suggestionList, Comparator.comparing(Suggestion::getID));
        ModelDisplayer.displayListOfDisplayable(suggestionList);

        String suggestionID = ScannerHelper.getIDInput("Enter the ID of an suggestion to reject (Enter 0 to cancel): ", IDHelper.extractSuggestionIDs(suggestionList), "Enter one of the IDs!");
        if (suggestionID.equals("0")) {
            System.out.println("Cancelling reply. Returning to Suggestion Menu...");
            return;
        }

        try {
            SuggestionManager.rejectSuggestion(suggestionID);
        } catch (ModelNotFoundException e) {
            System.out.println("Failed to reject. Returning to Suggestion Menu...");
        }
        System.out.println("Suggestion rejected successfully!");
    }
}



