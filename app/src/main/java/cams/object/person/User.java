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

}
