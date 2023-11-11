package cams.ui;

<<<<<<< HEAD
import cams.object.Camp;
=======
import cams.object.appitem.Camp;
>>>>>>> fabb6fec7474c08a61f3fda528bf8ba78271fc5b
import cams.object.person.Student;
import cams.util.ScannerHelper;

import java.util.List;
import java.util.Scanner;

public class CampCommitteeMenuUI extends BaseUI {

<<<<<<< HEAD
    private Student currentCommittee;
    private Scanner input = ScannerHelper.getScannerInput();

    public CampCommitteeMenuUI(Student committee) {
        this.currentCommittee = committee;
    }
=======
    //private Student currentCommittee;
    private Scanner input = ScannerHelper.getScannerInput();

    //public CampCommitteeMenuUI(Student committee) {
    //    this.currentCommittee = committee;
    //}
>>>>>>> fabb6fec7474c08a61f3fda528bf8ba78271fc5b

    @Override
    protected int generateMenuScreen() {
        printHeader("Camp Committee Menu");
        System.out.println("1) View Camp Details");
        System.out.println("2) Submit Suggestions");
        System.out.println("3) View and Reply to Enquiries");
        System.out.println("4) Edit, Delete, and View Suggestions");
        System.out.println("5) Generate Report");
        System.out.println("6) Track Points");
<<<<<<< HEAD
        System.out.println("7) Exit Camp Committee Menu");
=======
        System.out.println("0) Exit Camp Committee Menu");
>>>>>>> fabb6fec7474c08a61f3fda528bf8ba78271fc5b
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
<<<<<<< HEAD
            case 7:
                return -1;
            default:
=======
            case 0:
                return -1;

            default:
                throw new MenuChoiceInvalidException("Camp Committee Menu");
>>>>>>> fabb6fec7474c08a61f3fda528bf8ba78271fc5b
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
