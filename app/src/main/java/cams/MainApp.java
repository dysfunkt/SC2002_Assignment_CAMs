package cams;
import cams.util.*;
import java.util.*;
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
        
        System.out.println(new MainApp().getGreeting());
        int selection;
        selection = ScannerHelper.getIntegerInput("Enter an integer: ");
        System.out.println(selection);
        
        
    }
}
