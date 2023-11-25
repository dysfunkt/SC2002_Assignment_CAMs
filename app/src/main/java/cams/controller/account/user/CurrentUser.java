package cams.controller.account.user;

import cams.model.person.User;

/**
 * A utility class to store the user that is currently logged in.
 */
public class CurrentUser {

    /**
     * User currently logged in.
     */
    private static User currentUser;

    /**
     * Instance of this class.
     */
    private static CurrentUser mInstance;

    /**
     * Default constructor of this class.
     */
    private CurrentUser() {
        currentUser = null;
    }

    /**
     * Initialises an instance of this class.
     */
    public static void init() {
        if (mInstance == null) mInstance = new CurrentUser(); 
    }

    
    /** 
     * Get the user currently logged on.
     * @return User currently logged on.
     */
    public static User get() {
        return currentUser;
    }

    
    /** 
     * Set the user currently logged on.
     * @param user User currently logged on.
     */
    public static void set(User user) {
        currentUser = user;
    }
    
}
