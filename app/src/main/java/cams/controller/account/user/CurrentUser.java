package cams.controller.account.user;

import cams.model.person.User;


public class CurrentUser {
    private static User currentUser;
    private static CurrentUser mInstance;

    private CurrentUser() {
        currentUser = null;
    }

    public static void init() {
        if (mInstance == null) mInstance = new CurrentUser(); 
    }

    
    /** 
     * @return User
     */
    public static User get() {
        return currentUser;
    }

    public static void set(User user) {
        currentUser = user;
    }
    
}
