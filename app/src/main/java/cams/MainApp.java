package cams;
import cams.object.appitem.Camp;
import cams.util.*;
import cams.object.person.*;
import cams.object.appitem.Camp;
import cams.ui.LoginMenuUI;

import java.io.IOException;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Main Application Class
 * Entry point of the CAMs application
 * This class should initialise all necessary items of the application and read all data from CSVs
 * 
 */

public class MainApp {

    public static User currentUser;
    public static ArrayList<Student> students;
    public static ArrayList<Staff> staffs;
    public static ArrayList<Camp> camps;

    public static void init() {
        StudentCSVHelper studentCsv = StudentCSVHelper.getInstance();
        StaffCSVHelper staffCsv = StaffCSVHelper.getInstance();
        CampCSVHelper campCsv = CampCSVHelper.getInstance();

        try {
            System.out.println("Loading Student infomation from file...");
            students = studentCsv.readFromCsv();
            System.out.println(students.size() + " students loaded successfully");

            System.out.println("Loading Staff infomation from file...");
            staffs = staffCsv.readFromCsv();
            System.out.println(staffs.size() + " staffs loaded successfully");

            System.out.println("Loading Camp infomation from file...");
            camps = campCsv.readFromCSV();
            System.out.println(camps.size() + " camps loaded successfully");
        } catch (IOException e) {
            System.out.println("[ERROR] Failed to read CSV from data folder. (" + e.getLocalizedMessage() + ")");
        }

        System.out.println("Initializing Program...");

        printWelcomeAscii();

        getGreeting();
    }

    public static boolean saveAll() {
        StudentCSVHelper studentCSVHelper = StudentCSVHelper.getInstance();
        StaffCSVHelper staffCSVHelper = StaffCSVHelper.getInstance();
        CampCSVHelper campCSVHelper = CampCSVHelper.getInstance();

        try {
            System.out.println("Saving current Student infomation to file...");
            studentCSVHelper.writeToCsv(students);
            System.out.println("Student List Saved!");

            System.out.println("Saving current Staff infomation to file...");
            staffCSVHelper.writeToCsv(staffs);
            System.out.println("Staff List Saved!");

            System.out.println("Saving current Camp infomation to file...");
            campCSVHelper.writeToCsv(camps);
            System.out.println("Camp List Saved!");

        } catch (IOException e) {
            System.out.println("[ERROR] Failed to save items to file. (" + e.getLocalizedMessage() + ")");
            return false;
        }
        return true;
    }


    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            saveAll();
            System.out.println("Shutting down program...");
        }));

        init();
        //call menu login menu
        new LoginMenuUI().startMainMenu();
        System.exit(0);
    }

    private static void printWelcomeAscii() {
        try {
            BufferedReader reader = FileIOHelper.getFileBufferedReader("welcome_ascii.txt");
            List<String> ascii = reader.lines().collect(Collectors.toList());
            ascii.forEach(System.out::println);
        } catch (IOException e) {
            System.out.println("[ERROR] Failed to load ASCII Welcome Art!");
        }
    }
    public static void getGreeting() {
    System.out.println("Welcome to Camp Management System!");
    }
}
