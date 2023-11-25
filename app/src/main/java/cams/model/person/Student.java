package cams.model.person;

import java.util.ArrayList;
import java.util.List;

import cams.util.iocontrol.CSVStringHelper;


/**
 * Represents a student in the system. It extends the user class.
 */
public class Student extends User{

    /**
     * List of campIDs that the student has joined.
     */
    private ArrayList<String> joinedCamps;

    /**
     * Indicates whether the student is part of a camp committee.
     */
    private Boolean campCommittee;

    /**
     * The number of points accumulated by the student.
     */
    private int points;

    /**
     * Id of camp for which the student is a committee member for.
     */
    private String campIDCommittingfor;


    /**
     * @param name Name of the student.
     * @param email Email of the student.
     * @param facultyString Faculty the student belongs to.
     * @param userID Id of the student.
     * @param password Password of the student.
     * @param firstLogin Indicates whether it's the first login for the student.
     * @param campCommittee Indicates whether the student is part of a camp committee.
     * @param points The number of points accumulated by the student.
     * @param campIDCommittingfor Id of the camp the student is committeeing for.
     * @param joinedCamps List of campIDs the student has joined.
     */
    public Student(String name, String email, String facultyString, String userID, String password, Boolean firstLogin,
                    Boolean campCommittee, int points, String campIDCommittingfor, ArrayList<String> joinedCamps) {

        super(name, email, facultyString, userID, password, firstLogin);
        this.campCommittee = campCommittee;
        this.points = points;
        this.campIDCommittingfor = campIDCommittingfor;
        this.joinedCamps = joinedCamps;
    }

    /**
     * Construct a student object from the csv containing the information of the student.
     * @param csv array return the csv values representing the student
     */
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

    /**
     * Check if the student is part of a camp committee.
     * @return True if the student is part of a camp committee, false otherwise.
     */
    public Boolean isCampCommittee() {
        return campCommittee;
    }

    /**
     * Gets the number of points accumulated by the student.
     * @return the number of points accumulated by the student.
     */
    public int getPoints(){
        return this.points;
    }

    /**
     * Increases the number of points the student has by 1.
     */
    public void increasePoints(){
        this.points++;
    }

    /**
     * Camp that student is joining.
     * @param campID Id of the camp that student is joining.
     */
    public void joinCamp(String campID) {
        this.joinedCamps.add(campID);
    }

    /**
     * Camp that the student is joining the committee for.
     * @param campID Id of the camp that the student is joining the committee for.
     */
    public void joinCommittee(String campID) {
        this.joinedCamps.add(campID);
        this.campIDCommittingfor = campID;
        campCommittee = true;
    }

    /**
     * Checks if the student has joined a specific camp.
     * @param campID Id of the camp to be checked.
     * @return True if the student has joined the camp, false otherwise.
     */
    public Boolean checkJoined(String campID) {
        if (this.joinedCamps.contains(campID)) return true;
        else return false;
    }

    /**
     * Gets the list of campIDs the student has joined
     * @return List of campIDs the student has joined.
     */
    public List<String> getJoinedCamps() {
        return joinedCamps;
    }

    /**
     * Removes the student from the specific camp he is leaving from.
     * @param ID ID of the camp the student is leaving.
     */
    public void leaveCamp(String ID) {
        if (joinedCamps.contains(ID)) {
            joinedCamps.remove(ID);
        }
    }

    /**
     * Gets the ID of the camp for which the student is a committee member for.
     * @return String return the id of the camp the student is committeeing for.
     */
    public String getCampIDCommittingFor() {
        return campIDCommittingfor;
    }
}
