package cams.ui;

import cams.MainApp;
import cams.object.Camp;
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
    private Staff currentStaff;
    private Scanner input = ScannerHelper.getScannerInput();

    public StaffMenuUI(Staff staff){
        this.currentStaff = staff;
    }
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
                CreateCamp(currentStaff);
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

    private void CreateCamp(Staff staff) {
        String campName;
        Date startDate;
        Date endDate;
        Date regCloseDate;
        ArrayList<eFaculty> userGroup;
        String campLocation;
        int campTotalSlots;
        int campCommitteeSlots;
        String campDescription;        
        boolean visibility;

        // In case the previous input was a primitive data type
        input.nextLine();

        // Set camp details
        System.out.print("Enter Camp Name: ");
        campName = input.nextLine();

        startDate = ScannerHelper.getDateInput("Enter the date (yyyy-MM-DD) that the camp starts (terminate with 0):");

        endDate = ScannerHelper.getDateInput("Enter the date (yyyy-MM-DD) that the camp starts (terminate with 0):");

        regCloseDate = ScannerHelper.getDateInput("Enter the date (yyyy-MM-DD) that the registration ends (terminate with 0):");

        userGroup = ScannerHelper.getEnumsInput("Enter one of the faculties (terminate with 0):");

        System.out.print("Enter Camp Location: ");
        campLocation = input.nextLine();

        campTotalSlots = ScannerHelper.getIntegerInput("Enter total # of slots for students: ");

        campCommitteeSlots = ScannerHelper.getIntegerInput("Enter total # of slots for camp committee: ");

        System.out.print("Enter Camp Description: ");
        campDescription = input.nextLine();

        staffInCharge = staff;

        System.out.print("Enter Visibility (true or false): ");
        visibility = input.nextBoolean();

        listOfAttendees = ScannerHelper.getStudentsInput("Enter the name of the student that will be involved with the camp (terminate with 0):");

        Camp campCreated = new Camp(campName, campDates, regCloseDate, userGroup, campLocation, campTotalSlots, campCommitteeSlots, campDescription, staffInCharge, listOfAttendees,visibility);
        ArrayList<Camp> campsInCharge = currentStaff.getCampsInCharge();
        campsInCharge.add(campCreated);
        currentStaff.setCampsInCharge(campsInCharge);
    }

    private void EditCamp() {
//        System.out.println("Enter the name of the camp you would want to edit: ");
//        ArrayList<Camp> campsInCharge = currentStaff.getCampsInCharge();
//        int campCount = 1;
//        for(Camp camp: campsInCharge){
//            System.out.println(campCount + ": " + camp.getCampName());
//        }
//        input.nextLine();
//        String campName = input.nextLine();


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
