package cams.model.person;

import java.util.ArrayList;

import cams.util.iocontrol.CSVStringHelper;


public class Student extends User{

    private ArrayList<Integer> joinedCamps;
    private Boolean campCommittee;
    private int points;
    private int campIDCommittingfor;
    
    

    public Student(String name, String email, String facultyString, String userID, String password, Boolean firstLogin, 
                    Boolean campCommittee, int points, int campIDCommittingfor, ArrayList<Integer> joinedCamps) {

        super(name, email, facultyString, userID, password, firstLogin);
        this.campCommittee = campCommittee;
        this.points = points;
        this.campIDCommittingfor = campIDCommittingfor;
        this.joinedCamps = joinedCamps;
    }
    public Student(String[] csv) {
        super(csv);
        this.joinedCamps = CSVStringHelper.CSVStringtoArraylistInteger(csv[6]);
        this.campCommittee = Boolean.valueOf(csv[7]);
        this.points = Integer.valueOf(csv[8]);
        this.campIDCommittingfor = Integer.valueOf(csv[9]);
        ;
    }

    @Override
    public String[] toSaveString() {
        String[] s = new String[10];
        String[] u = super.toSaveString();
        for (int i = 0; i < 6; i++) {
            s[i] = u[i];
        }
        s[6] = CSVStringHelper.arraylistIntegertoCSVString(this.joinedCamps);
        s[7] = this.campCommittee + "";
        s[8] = this.points + "";
        s[9] = this.campIDCommittingfor + "";
    
        return s;
    }

    public Boolean isCampCommittee() {
        return campCommittee;
    }

    public int getPoints(){
        return this.points;
    }

    public void increasePoints(){
        this.points++;
    }

    public void joinCamp(int campID) {
        this.joinedCamps.add(campID);
    }

    public void joinCommittee(int campID) {
        this.joinedCamps.add(campID);
        this.campIDCommittingfor = campID;
        campCommittee = true;
    }

    public Boolean checkJoined(int campID) {
        if (this.joinedCamps.contains(campID)) return true;
        else return false;
    }

    public ArrayList<Integer> getJoinedCamps() {
        return joinedCamps;
    }

    public int getCampIDCommittingFor() {
        return campIDCommittingfor;
    }
}
