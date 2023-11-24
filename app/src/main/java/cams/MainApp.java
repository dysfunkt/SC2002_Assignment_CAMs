package cams;


import cams.util.ui.Welcome;
import cams.boundary.login.LoginMenuUI;
import cams.controller.account.user.CurrentUser;
import cams.controller.repository.RepositoryManager;
import cams.controller.repository.UniqueIDHandler;


import java.io.IOException;


/**
 * Main Application Class
 * Entry point of the CAMs application
 * This class should initialise all necessary items of the application and read all data from CSVs
 * 
 */

public class MainApp {

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
}
