package cams;
import cams.util.*;
import cams.util.iocontrol.CampCSVHelper;
import cams.util.iocontrol.EnquiryCSVHelper;
import cams.util.iocontrol.FileIOHelper;
import cams.util.iocontrol.StaffCSVHelper;
import cams.util.iocontrol.StudentCSVHelper;
import cams.util.iocontrol.SuggestionCSVHelper;
import cams.util.iocontrol.UniqueIDCSVHelper;
import cams.boundary.LoginMenuUI;
import cams.controller.account.user.CurrentUser;
import cams.controller.repository.RepositoryManager;
import cams.model.appitem.*;
import cams.model.person.*;

import java.io.IOException;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Main Application Class
 * Entry point of the CAMs application
 * This class should initialise all necessary items of the application and read all data from CSVs
 * 
 */

public class MainApp {
    public static Date todayDate;
    public static User currentUser;
    public static UniqueID uniqueID;
    public static ArrayList<Student> students;
    public static ArrayList<Staff> staffs;
    public static ArrayList<Camp> camps;
    public static ArrayList<Enquiry> enquiries;
    public static ArrayList<Suggestion> suggestions;

    public static void init() {
        UniqueIDCSVHelper uniqueIDCsv = UniqueIDCSVHelper.getInstance();
        //StudentCSVHelper studentCsv = StudentCSVHelper.getInstance();
        //StaffCSVHelper staffCsv = StaffCSVHelper.getInstance();
        CampCSVHelper campCsv = CampCSVHelper.getInstance();
        EnquiryCSVHelper enquiryCsv = EnquiryCSVHelper.getInstance();
        SuggestionCSVHelper suggestionCsv = SuggestionCSVHelper.getInstance();

        RepositoryManager.init();
        CurrentUser.init();
        try {
            System.out.println("Loading UniqueID infomation from file...");
            uniqueID = uniqueIDCsv.readFromCsv();
            System.out.println("UniqueID loaded successfully");

            /* 
            System.out.println("Loading Student infomation from file...");
            students = studentCsv.readFromCsv();
            System.out.println(students.size() + " students loaded successfully");
            */
            /* 
            System.out.println("Loading Staff infomation from file...");
            staffs = staffCsv.readFromCsv();
            System.out.println(staffs.size() + " staffs loaded successfully");
            */
            System.out.println("Loading Camp infomation from file...");
            camps = campCsv.readFromCsv();
            System.out.println(camps.size() + " camps loaded successfully");

            System.out.println("Loading Enquiry infomation from file...");
            enquiries = enquiryCsv.readFromCsv();
            System.out.println(enquiries.size() + " enquiries loaded successfully");

            System.out.println("Loading Suggestion infomation from file...");
            suggestions = suggestionCsv.readFromCsv();
            System.out.println(suggestions.size() + " suggestions loaded successfully");
        } catch (IOException e) {
            System.out.println("[ERROR] Failed to read CSV from data folder. (" + e.getLocalizedMessage() + ")");
        }

        todayDate = getTodayDate();

        System.out.println("Initializing Program...");

        printWelcomeAscii();

        getGreeting();
    }

    public static boolean saveAll() {
        UniqueIDCSVHelper uniqueIDCSVHelper = UniqueIDCSVHelper.getInstance();
        //StudentCSVHelper studentCSVHelper = StudentCSVHelper.getInstance();
        //StaffCSVHelper staffCSVHelper = StaffCSVHelper.getInstance();
        CampCSVHelper campCSVHelper = CampCSVHelper.getInstance();
        EnquiryCSVHelper enquiryCSVHelper = EnquiryCSVHelper.getInstance();
        SuggestionCSVHelper suggestionCSVHelper = SuggestionCSVHelper.getInstance();

        RepositoryManager.saveAll();
        try {
            System.out.println("Saving current UniqueID infomation to file...");
            uniqueIDCSVHelper.writeToCsv(uniqueID);
            System.out.println("UniqueID Saved!");

            /* 
            System.out.println("Saving current Student infomation to file...");
            studentCSVHelper.writeToCsv(students);
            System.out.println("Student List Saved!");
            */
            /* 
            System.out.println("Saving current Staff infomation to file...");
            staffCSVHelper.writeToCsv(staffs);
            System.out.println("Staff List Saved!");
            */
            System.out.println("Saving current Camp infomation to file...");
            campCSVHelper.writeToCsv(camps);
            System.out.println("Camp List Saved!");

            System.out.println("Saving current Enquiry infomation to file...");
            enquiryCSVHelper.writeToCsv(enquiries);
            System.out.println("Enquiry List Saved!");

            System.out.println("Saving current Suggestion infomation to file...");
            suggestionCSVHelper.writeToCsv(suggestions);
            System.out.println("Suggestion List Saved!");
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
        System.out.println("Today's date: " + todayDate);
    }

    public static Date getTodayDate() {
        java.time.LocalDate today = java.time.LocalDate.now();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(today.toString());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
