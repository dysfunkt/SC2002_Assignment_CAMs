package cams.object.person;

public class Staff extends User{
    public Staff(String name, String email, String facultyString, String userID, String password, Boolean firstLogin) {
        super(name, email, facultyString, userID, password, firstLogin);
    }
    public Staff(String[] csv) {
        super(csv);
    }
}
