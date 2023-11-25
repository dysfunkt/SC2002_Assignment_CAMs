package cams.model.person;

import cams.model.Model;


/**
 * Represents a user in the system.
 */
public class User implements Model{
    private String name;
    private String email;
    private eFaculty faculty;
    private String userID;
    private String password;
    private Boolean firstLogin;

    /**
     * @param name Name of the user.
     * @param email Email of the user.
     * @param facultyString Faculty the user belongs to.
     * @param userID Id of the user
     * @param password Password of the user.
     * @param firstLogin Indicates whether it's the user's first login.
     */
    public User(String name, String email, String facultyString, String userID, String password, Boolean firstLogin) {
        this.name = name;
        this.email = email;
        this.faculty = Enum.valueOf(eFaculty.class, facultyString);
        this.userID = userID;
        this.password = password;
        this.firstLogin = firstLogin;
    }

    /**
     * Construct a user object from the csv containing the information of the user.
     * @param csv array return the csv values representing the user
     */
    public User(String[] csv) {
        this.name = csv[0];
        this.email = csv[1];
        this.faculty = Enum.valueOf(eFaculty.class, csv[2]);
        this.userID = csv[3];
        this.password = csv[4];
        this.firstLogin = Boolean.valueOf(csv[5]);
    }

    
    /**
     * Converts the user's details to a string array for saving.
     * @return String[] that represents the user's details.
     */
    public String[] toSaveString() {
        String[] s = new String[6];
        s[0] = this.name;
        s[1] = this.email;
        s[2] = this.faculty + "";
        s[3] = this.userID;
        s[4] = this.password;
        s[5] = this.firstLogin + "";

        return s;
    }

    /**
     * Gets the email of the user.
     * @return String of the users email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the faculty the user belongs to.
     * @return the faculty that the user belongs to.
     */
    public eFaculty getFaculty() {
        return faculty;
    }

    /**
     * Gets the name of the user.
     * @return String of the name of the user.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the Id of the user
     * @return String of the Id of the user
     */
    public String getID() {
        return userID;
    }

    /**
     * Checks if the entered password matches the users's password
     * @param p the password to check.
     * @return True if the passwords match, false otherwise.
     */
    public Boolean checkPassword(String p) {
        if (p.equals(password)) {
            return true;
        } else return false;
    }

    /**
     * Change the user's password.
     * @param p the new password to change to.
     */
    public void changePassword(String p) {
        password = p;
        firstLogin = false;
        System.out.println("Password changed successfully!");
    }

    /**
     * Check if its the user's first login
     * @return True if it's the first login, false otherwise.
     */
    public Boolean isFirstLogin() {
        return firstLogin;
    }

}
