package cams.model.person;

import java.util.ArrayList;
import java.util.List;

import cams.util.iocontrol.CSVStringHelper;


public class Student extends User{

    private ArrayList<String> joinedCamps;
    private Boolean campCommittee;
    private int points;
    private String campIDCommittingfor;
    
    

    public Student(String name, String email, String facultyString, String userID, String password, Boolean firstLogin, 
                    Boolean campCommittee, int points, String campIDCommittingfor, ArrayList<String> joinedCamps) {

        super(name, email, facultyString, userID, password, firstLogin);
        this.campCommittee = campCommittee;
        this.points = points;
        this.campIDCommittingfor = campIDCommittingfor;
        this.joinedCamps = joinedCamps;
    }
    public Student(String[] csv) {
        super(csv);
        this.joinedCamps = CSVStringHelper.CSVStringtoArraylistString(csv[6]);
        this.campCommittee = Boolean.valueOf(csv[7]);
        this.points = Integer.valueOf(csv[8]);
        this.campIDCommittingfor = csv[9];
        ;
    }

    
    /** 
     * @return String[]
     */
    @Override
    public String[] toSaveString() {
        String[] s = new String[10];
        String[] u = super.toSaveString();
        for (int i = 0; i < 6; i++) {
            s[i] = u[i];
        }
        s[6] = CSVStringHelper.arraylistStringtoCSVString(this.joinedCamps);
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

    public void joinCamp(String campID) {
        this.joinedCamps.add(campID);
    }

    public void joinCommittee(String campID) {
        this.joinedCamps.add(campID);
        this.campIDCommittingfor = campID;
        campCommittee = true;
    }

    public Boolean checkJoined(String campID) {
        if (this.joinedCamps.contains(campID)) return true;
        else return false;
    }

    public List<String> getJoinedCamps() {
        return joinedCamps;
    }

    public void leaveCamp(String ID) {
        if (joinedCamps.contains(ID)) {
            joinedCamps.remove(ID);
        }
    }

    public String getCampIDCommittingFor() {
        return campIDCommittingfor;
    }
}
