package cams.ui;

import cams.object.appitem.Camp;
import cams.object.person.Student;
import cams.util.ScannerHelper;

import java.util.List;
import java.util.Scanner;

public class CampCommitteeMenuUI extends BaseUI {

    //private Student currentCommittee;
    private Scanner input = ScannerHelper.getScannerInput();

    //public CampCommitteeMenuUI(Student committee) {
    //    this.currentCommittee = committee;
    //}

    @Override
    protected int generateMenuScreen() {
        printHeader("Camp Committee Menu");
        System.out.println("1) View Camp Details");
        System.out.println("2) Submit Suggestions");
        System.out.println("3) View and Reply to Enquiries");
        System.out.println("4) Edit, Delete, and View Suggestions");
        System.out.println("5) Generate Report");
        System.out.println("6) Track Points");
        System.out.println("0) Exit Camp Committee Menu");
        printBreaks();

        int choice = doMenuChoice(7, 0);
        switch (choice) {
            case 1:
                viewCampDetails();
                break;
            case 2:
                submitSuggestions();
                break;
            case 3:
                viewAndReplyToEnquiries();
                break;
            case 4:
                editDeleteAndViewSuggestions();
                break;
            case 5:
                generateReport();
                break;
            case 6:
                trackPoints();
                break;
            case 0:
                return -1;

            default:
                throw new MenuChoiceInvalidException("Camp Committee Menu");
        }
        return 0;
    }

    private void viewCampDetails() {
        // Implement logic to view camp details
    }

    private void submitSuggestions() {
        // Implement logic to submit suggestions
    }

    private void viewAndReplyToEnquiries() {
        // Implement logic to view and reply to enquiries
    }

    private void editDeleteAndViewSuggestions() {
        // Implement logic to edit, delete, and view suggestions
    }

    private void generateReport() {
        // Implement logic to generate a report
    }

    private void trackPoints() {
        // Implement logic to track points for enquiries and suggestions
    }
}
