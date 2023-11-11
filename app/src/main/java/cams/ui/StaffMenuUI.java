package cams.ui;

import cams.MainApp;
import cams.object.appitem.Camp;
import cams.object.person.eFaculty;
import cams.util.ScannerHelper;

import java.util.Date;
import java.util.Scanner;


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
        System.out.println("10) Log out");
        System.out.println("0) Exit Application");
        printBreaks();
        int choice = doMenuChoice(10, 0);
        switch (choice) {
            case 1:
                CreateCamp();
                break;
            case 2:
                EditCamp();
                break;
            case 3:
                DeleteCamp();
                break;
            case 4:
                ViewAllCamps();
                break;
            case 5:
                ViewYourCamp();
                break;
            case 6:
                ViewAllEnquiries();
                break;
            case 7:
                ViewAllSuggestions();
                break;
            case 8:
                GenerateCampReport();
                break;
            case 9:
                GeneratePerformanceReport();
                break;
            case 10:
                System.out.println("You have successfully logged out.");
                return -1;
            case 0:
                System.out.println("Closing application...");
                return 1; //shutdown
            default:
                throw new MenuChoiceInvalidException("Login Menu");
        }
        return 0;
    }

    private void CreateCamp() {
        int campID;
        String campName;
        Date startDate;
        Date endDate;
        Date regCloseDate;
        eFaculty userGroup;
        String campLocation;
        int campTotalSlots;
        int campCommitteeSlots;
        String campDescription;   
        String staffInCharge;   
        Boolean visibility;  

        // In case the previous input was a primitive data type
        input.nextLine();

        // Set camp details
        campID = MainApp.uniqueID.getNextCampID();
        MainApp.uniqueID.incrementCampID();

        System.out.print("Enter Camp Name: ");
        campName = input.nextLine();

        startDate = ScannerHelper.getDateInput("Enter the date (yyyy-MM-DD) that the camp starts (terminate with 0): ");

        endDate = ScannerHelper.getDateInput("Enter the date (yyyy-MM-DD) that the camp starts (terminate with 0): ");

        regCloseDate = ScannerHelper.getDateInput("Enter the date (yyyy-MM-DD) that the registration ends (terminate with 0): ");

        int choice = ScannerHelper.getIntegerInput("Set group camp is open to (1 for your faculty, 2 for whole NTU): ", 0 ,3);
        switch (choice) {
            case 1: 
                userGroup = MainApp.currentUser.getFaculty();
                break;
            case 2:
                userGroup = Enum.valueOf(eFaculty.class, "NTU");
                break;
            default: 
                userGroup = MainApp.currentUser.getFaculty();
        }

        System.out.print("Enter Camp Location: ");
        campLocation = input.nextLine();

        campTotalSlots = ScannerHelper.getIntegerInput("Enter total # of slots for students: ");

        campCommitteeSlots = ScannerHelper.getIntegerInput("Enter total # of slots for camp committee (Max 10): ", 0, 11);

        System.out.print("Enter Camp Description: ");
        campDescription = input.nextLine();

        staffInCharge = MainApp.currentUser.getUserID();

        visibility = ScannerHelper.getYesNoInput("Make camp visible?");

        
        MainApp.camps.add(new Camp(campID, campName, startDate, endDate, regCloseDate, userGroup, campLocation, campTotalSlots, campCommitteeSlots, campDescription, staffInCharge, visibility));
        
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
