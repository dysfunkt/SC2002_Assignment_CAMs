package cams.object.person;

public class User {
    private String name;
    private String email;
    private eFaculty faculty;
    private String userID;
    private String password;
    private Boolean firstLogin;

    public User(String name, String email, String facultyString, String userID, String password, Boolean firstLogin) {
        this.name = name;
        this.email = email;
        this.faculty = Enum.valueOf(eFaculty.class, facultyString);
        this.userID = userID;
        this.password = password;
        this.firstLogin = firstLogin;
    }

    public User(String[] csv) {
        this.name = csv[0];
        this.email = csv[1];
        this.faculty = Enum.valueOf(eFaculty.class, csv[2]);
        this.userID = csv[3];
        this.password = csv[4];
        this.firstLogin = Boolean.valueOf(csv[5]);
    }

    public String[] toCsv() {
        String[] s = new String[6];
        s[0] = this.name;
        s[1] = this.email;
        s[2] = this.faculty + "";
        s[3] = this.userID;
        s[4] = this.password;
        s[5] = this.firstLogin + "";

        return s;
    }

    public String getEmail() {
        return email;
    }
    public eFaculty getFaculty() {
        return faculty;
    }
    public String getName() {
        return name;
    }
    public String getUserID() {
        return userID;
    }
    public Boolean checkPassword(String p) {
        if (p.equals(password)) {
            return true;
        } else return false;
    }
    public void changePassword(String p) {
        password = p;
        firstLogin = false;
        System.out.println("Password changed successfully!");
    }
    public Boolean isFirstLogin() {
        return firstLogin;
    }

}
