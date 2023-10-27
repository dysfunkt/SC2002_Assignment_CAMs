package cams;

import java.util.Scanner;
/**
 * Main Application Class
 * Entry point of the CAMs application
 * This class should initialise all necessary items of the application and read all data from CSVs
 * 
 */

public class MainApp {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println(new MainApp().getGreeting());
        System.out.println("input string:");
        System.out.println(sc.next());
    }
}
