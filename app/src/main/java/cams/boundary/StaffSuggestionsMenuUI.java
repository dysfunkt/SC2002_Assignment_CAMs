package cams.boundary;

import java.util.Scanner;

import cams.MainApp;
import cams.model.appitem.*;
import cams.util.ScannerHelper;

public class StaffSuggestionsMenuUI extends BaseUI{

    private Scanner input = ScannerHelper.getScannerInput();

    protected int generateMenuScreen() {
        printHeader("Staff Suggestions Menu");
        System.out.println("1) View All Suggestions");
        System.out.println("2) Approve a Suggestion");
        System.out.println("3) Return to Staff Menu");
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


    public void viewAllSuggestions() {
        printHeader("View All Suggestions");

        // Assuming 'suggestions' is a list containing all Suggestion objects
        for (Suggestion suggestion : MainApp.suggestions) {
            // Display relevant information about each suggestion
            System.out.println("Suggestion ID: " + suggestion.getID());
            System.out.println("Camp ID: " + suggestion.getCampID());
            System.out.println("Created By: " + suggestion.getCreatedBy());
            System.out.println("Suggestion Message: " + suggestion.getSuggestionMessage());
            System.out.println("Processed: " + suggestion.isProcessed());
            System.out.println("Deleted: " + suggestion.isDeleted());
            System.out.println("Approved: " + suggestion.isApproved());
            System.out.println("-----------------------------");
        }
        printBreaks();
    }


    public void approveSuggestion() {
    printHeader("Approve Suggestion");
    System.out.print("Enter Suggestion ID to approve: ");
    String suggestionIDToApprove = input.nextInt()+"";
    input.nextLine(); // Consume the newline character

    // Find the Suggestion with the given ID
    Suggestion selectedSuggestion = null;
    for (Suggestion suggestion : MainApp.suggestions) {
        if (suggestion.getID().equals(suggestionIDToApprove) ) {
            selectedSuggestion = suggestion;
            break;
        }
    }

    // Check if the suggestion was found
    if (selectedSuggestion != null) {
        if (!selectedSuggestion.isProcessed()) {
            // Set the suggestion as approved
            selectedSuggestion.approve(true);

            System.out.println("Suggestion approved successfully!");
        } else {
            System.out.println("Suggestion with ID " + suggestionIDToApprove + " has already been processed.");
        }
    } else {
        System.out.println("Suggestion with ID " + suggestionIDToApprove + " not found.");
    }

    printBreaks();
    }
}



