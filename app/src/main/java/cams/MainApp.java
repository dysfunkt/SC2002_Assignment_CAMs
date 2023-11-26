package cams;

import cams.util.ui.Welcome;
import cams.boundary.login.LoginMenuUI;
import cams.controller.account.user.CurrentUser;
import cams.controller.repository.RepositoryManager;
import cams.controller.repository.UniqueIDHandler;

import java.io.IOException;
import java.util.Scanner;

/**
 * Main Application Class
 * Entry point of the CAMs application
 * This class will handle initialisation upon startup and saving app data upon shutdown.
 */

public class MainApp {
    /** 
     * Loads app data from data files and initialises CurrentUser.
     * Then it displays the welcome page.
     */
    public static void init() {
        System.out.println("Initializing Program...");
        RepositoryManager.loadAll();
        try {
            System.out.println("Loading UniqueID infomation from file...");
            UniqueIDHandler.getInstance().load();
            System.out.println("UniqueID loaded successfully");

        } catch (IOException e) {
            System.out.println("[ERROR] Failed to read CSV from data folder. (" + e.getLocalizedMessage() + ")");
        }
        CurrentUser.init();
        Welcome.printWelcomeAscii();
        Welcome.getGreeting();
    }

    
    /** 
     * Saves all data into its relevant CSV files on disk
     * @return true if successful, false otherwise
     */
    public static boolean saveAll() {
        RepositoryManager.saveAll();
        try {
            System.out.println("Saving current UniqueID infomation to file...");
            UniqueIDHandler.getInstance().save();
            System.out.println("UniqueID Saved!");

        } catch (IOException e) {
            System.out.println("[ERROR] Failed to save items to file. (" + e.getLocalizedMessage() + ")");
            return false;
        }
        return true;
    }


    
    /** 
     * Entry point of the program.
     * Sets shutdown hook to save data upon shutdown.
     * Calls init method to load app data.
     * Initialises LoginMenuUI class and starts the program by invoking start main menu of LoginMenuUI
     * @param args Command line arguments passed to the program (Not used in this implementation)
     */
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            saveAll();
            System.out.println("Shutting down program...");
            new Scanner(System.in).nextLine();
        }));

        init();
        //call menu login menu
        new LoginMenuUI().startMainMenu();
        System.exit(0);
    }
}
