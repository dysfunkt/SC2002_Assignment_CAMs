package cams.object.person;

public class Student extends User{
    public Student(String name, String email, String facultyString, String userID, String password) {
        super(name, email, facultyString, userID, password);
    }
    public Student(String[] csv) {
        super(csv);
    }
}
