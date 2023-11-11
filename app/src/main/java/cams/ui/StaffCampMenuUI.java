package cams.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import cams.MainApp;
import cams.object.appitem.Camp;
import cams.object.person.Staff;
import cams.object.person.eFaculty;
import cams.util.ScannerHelper;

public class StaffCampMenuUI extends BaseUI{

    private Scanner input = ScannerHelper.getScannerInput();

    protected int generateMenuScreen() {
        printHeader("Camps Menu");
        System.out.println("1) Create Camp");
        System.out.println("2) Edit Camp");
        System.out.println("3) Delete Camp");
        System.out.println("4) View All Camps");
        System.out.println("5) View Your Camps");
        System.out.println("6) Generate Camp Report");
        System.out.println("7) Return to Staff Menu");
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
                ViewYourCamps();
                break;
            case 6:
                GeneratePerformanceReport();
                break;
            case 7:
                System.out.println("Switching back to Staff Menu.");
                return -1;
            case 0:
                System.out.println("Closing application...");
                return 1; //shutdown
            default:
                throw new MenuChoiceInvalidException("Staff Camp Menu");
        }
        return 0;
    }
    private void CreateCamp() {
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

        MainApp.camps.add(new Camp(campName, startDate, endDate, regCloseDate, userGroup, campLocation, campTotalSlots, campCommitteeSlots, campDescription, staffInCharge, visibility));
    }

    private void EditCamp(){
        ArrayList<Camp> campsInCharge = ViewYourCamps();
        int campNo = ScannerHelper.getIntegerInput("Enter the number of the camp that you want to edit: ", 1, campsInCharge.size());
        Camp chosenCamp = campsInCharge.get(campNo - 1);

        new StaffEditCampMenu(chosenCamp).startMainMenu();
    }

    private void DeleteCamp(){
        ArrayList<Camp> campsInCharge = ViewYourCamps();
        int campNo = ScannerHelper.getIntegerInput("Enter the number of the camp that you want to delete: ", 1, campsInCharge.size());
        Camp chosenCamp = campsInCharge.get(campNo - 1);
        MainApp.camps.remove(chosenCamp);
    }

    private void ViewAllCamps() {
        int campCount = 1;
        for(Camp camp : MainApp.camps){
            System.out.println(campCount + ": " + camp.getCampName());
        }
    }

    private ArrayList<Camp> ViewYourCamps() {
        ArrayList<Camp> campsInCharge;

        // Down-casting check
        if (MainApp.currentUser instanceof Staff) {
            campsInCharge = ((Staff) MainApp.currentUser).getCampsInCharge();
        } else {
            System.out.println("Invalid - you have to be a Staff to edit camps");
            return null;
        }

        System.out.println("These are the camps under your charge: ");
        int campCount = 1;
        for(Camp camp: campsInCharge){
            System.out.println(campCount + ": " + camp.getCampName());
            campCount++;
        }
        return campsInCharge;
    }
    private void GeneratePerformanceReport(){}

}
