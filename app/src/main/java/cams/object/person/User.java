package cams.object.person;

public class User {
    private String name;
    private String email;
    private eFaculty faculty;
    private String userID;
    private String password;

    public User(String name, String email, String facultyString, String userID, String password) {
        this.name = name;
        this.email = email;
        this.faculty = Enum.valueOf(eFaculty.class, facultyString);
        this.userID = userID;
        this.password = password;
    }

    public User(String[] csv) {
        this.name = csv[0];
        this.email = csv[1];
        this.faculty = Enum.valueOf(eFaculty.class, csv[2]);
        this.userID = csv[3];
        this.password = csv[4];
    }

    public String[] toCsv() {
        String[] s = new String[5];
        s[0] = this.name;
        s[1] = this.email;
        s[2] = this.faculty + "";
        s[3] = this.userID;
        s[4] = this.password;

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
    public int checkPassword(String p) {
        if (p.equals(password)) {
            return 1;
        } else return 0;
    }


}
