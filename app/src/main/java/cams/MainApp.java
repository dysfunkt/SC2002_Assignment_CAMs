package cams;
import cams.util.*;
import cams.object.person.*;

import java.io.IOException;
import java.util.*;
/**
 * Main Application Class
 * Entry point of the CAMs application
 * This class should initialise all necessary items of the application and read all data from CSVs
 * 
 */

public class MainApp {

    public static ArrayList<Student> students;
    public static ArrayList<Staff> staffs;

    public static void init() {
        StudentCSVHelper studentCsv = StudentCSVHelper.getInstance();
        StaffCSVHelper staffCsv = StaffCSVHelper.getInstance();

        try {
            System.out.println("Loading Student infomation from file...");
            students = studentCsv.readFromCsv();
            System.out.println(students.size() + " students loaded successfully");

            System.out.println("Loading Staff infomation from file...");
            staffs = staffCsv.readFromCsv();
            System.out.println(staffs.size() + " staffs loaded successfully");
        } catch (IOException e) {
            System.out.println("[ERROR] Failed to read CSV from data folder. (" + e.getLocalizedMessage() + ")");
        }

        System.out.println("Initializing Program...");

        System.out.println(new MainApp().getGreeting());
    }

    public static boolean saveAll() {
        StudentCSVHelper studentCSVHelper = StudentCSVHelper.getInstance();
        StaffCSVHelper staffCSVHelper = StaffCSVHelper.getInstance();

        try {
            System.out.println("Saving current Student infomation to file...");
            studentCSVHelper.writeToCsv(students);
            System.out.println("Student List Saved!");

            System.out.println("Saving current Staff infomation to file...");
            staffCSVHelper.writeToCsv(staffs);
            System.out.println("Staff List Saved!");

        } catch (IOException e) {
            System.out.println("[ERROR] Failed to save items to file. (" + e.getLocalizedMessage() + ")");
            return false;
        }
        return true;
    }
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            saveAll();
            System.out.println("Shutting down program...");
        }));

        init();

        Student stdTmp = students.get(0);
        
        System.out.println(stdTmp.getName());
        
        Staff staTmp = staffs.get(0);
        
        System.out.println(staTmp.getName());
        
    }
}
