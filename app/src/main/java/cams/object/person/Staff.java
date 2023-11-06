package cams.object.person;

public class Staff extends User{
    public Staff(String name, String email, String facultyString, String userID, String password) {
        super(name, email, facultyString, userID, password);
    }
    public Staff(String[] csv) {
        super(csv);
    }
}
