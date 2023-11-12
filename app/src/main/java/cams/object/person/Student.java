package cams.object.person;

public class Student extends User{

    private Boolean campCommittee;
    private int campIDCommittingfor;
    

    public Student(String name, String email, String facultyString, String userID, String password, Boolean firstLogin, Boolean campCommittee, int campIDCommittingfor) {
        super(name, email, facultyString, userID, password, firstLogin);
        this.campCommittee = campCommittee;
    }
    public Student(String[] csv) {
        super(csv);
        this.campCommittee = Boolean.valueOf(csv[6]);
    }

    @Override
    public String[] toCsv() {
        String[] s = new String[7];
        String[] u = super.toCsv();
        for (int i = 0; i < 6; i++) {
            s[i] = u[i];
        }
        s[6] = campCommittee + "";
        return s;
    }

    public Boolean isCampCommittee() {
        return campCommittee;
    }
}
