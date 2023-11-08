package cams.ui;

import cams.MainApp;
import cams.object.person.Staff;
import cams.object.person.Student;
import cams.object.person.eFaculty;
import cams.util.ScannerHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class StaffMenuUI extends BaseUI {
    private Scanner input = ScannerHelper.getScannerInput();

    protected int generateMenuScreen() {
        printHeader("Login Menu");
        System.out.println("1) Create Camp");
        System.out.println("2) Edit Camp");
        System.out.println("3) Delete Camp");
        System.out.println("4) View All Camps");
        System.out.println("5) View Your Camps");
        System.out.println("6) View All Enquiries and Reply");
        System.out.println("7) View All Suggestions and Approve");
        System.out.println("8) Generate Camp Report");
        System.out.println("9) Generate Performance Report");
        System.out.println("0) Exit");
        printBreaks();
        int choice = doMenuChoice(2, 0);
        switch (choice) {
            case 1:
                CreateCamp();
                return 1;
            case 2:
                EditCamp();
                return 1;
            case 3:
                DeleteCamp();
                return 1;
            case 4:
                ViewAllCamps();
                return 1;
            case 5:
                ViewYourCamp();
                return 1;
            case 6:
                ViewAllEnquiries();
                return 1;
            case 7:
                ViewAllSuggestions();
                return 1;
            case 8:
                GenerateCampReport();
                return 1;
            case 9:
                GeneratePerformanceReport();
                return 1;
            case 0:
                return 1; //shutdown
            default:
                throw new MenuChoiceInvalidException("Login Menu");
        }
    }

    private void CreateCamp() {
        String campName;
        ArrayList<Date> campDates;
        Date regCloseDate;
        ArrayList<eFaculty> userGroup;
        String campLocation;
        int campTotalSlots;
        int campCommitteeSlots;
        String campDescription;
        Staff staffInCharge;
        ArrayList<Student> listOfAttendees;
        boolean visibility;

        // In case the previous input was a primitive data type
        input.nextLine();

        // Set camp details
        System.out.print("Enter Camp Name: ");
        campName = input.nextLine();

        // Take an array of dates
        campDates = ScannerHelper.getDatesInput("Enter the dates (yyyy-MM-DD) that the camp happens (terminate with 0):");

        // Set camp details
        regCloseDate = ScannerHelper.getDateInput("Enter the date (yyyy-MM-DD) that the registration ends (terminate with 0):");



    }

    private void EditCamp() {
    }

    private void DeleteCamp() {
    }

    private void ViewAllCamps() {
    }

    private void ViewYourCamp() {
    }

    private void ViewAllEnquiries() {
    }

    private void ViewAllSuggestions() {
    }

    private void GenerateCampReport() {
    }

    private void GeneratePerformanceReport() {
    }
}
