package cams.ui;

import java.util.Scanner;

import cams.util.ScannerHelper;

public class StaffSuggestionsMenuUI extends BaseUI{

    private Scanner input = ScannerHelper.getScannerInput();

    protected int generateMenuScreen() {
        printHeader("Suggestions Menu");
        System.out.println("1) View All Suggestions");
        System.out.println("2) Approve a Suggestion");
        System.out.println("3) Return to Staff Menu");
        System.out.println("0) Exit Application");
        printBreaks();
        int choice = doMenuChoice(10, 0);
        switch (choice) {
            case 1:
                ViewAllSuggestions();
                break;
            case 2:
                ApproveSuggestion();
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

   public void ApproveSuggestion() {
    printHeader("Approve Suggestion");
    System.out.print("Enter Suggestion ID to approve: ");
    int suggestionIDToApprove = input.nextInt();
    input.nextLine(); // Consume the newline character

    // Find the Suggestion with the given ID
    Suggestion selectedSuggestion = null;
    for (Suggestion suggestion : suggestions) {
        if (suggestion.getSuggestionID() == suggestionIDToApprove) {
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



