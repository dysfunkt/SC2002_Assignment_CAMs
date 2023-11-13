package cams.ui;

import java.util.Scanner;

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

    public void ViewAllSuggestions(){}
    public void ApproveSuggestion(){}
}

