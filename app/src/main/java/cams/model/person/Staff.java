package cams.model.person;

import java.util.ArrayList;

import cams.util.iocontrol.CSVStringHelper;

/**
 * This class represents a staff member in the system
 */
public class Staff extends User{

    /**
     * List of camp IDs that the staff member is in charge of.
     */
    private ArrayList<String> campsInChargeID;

    /**
     * CSV padding
     */
    private String csvpad;

    /**
     * @param name Name of the staff member.
     * @param email Email of the staff member.
     * @param facultyString Faculty that the staff member belongs to.
     * @param userID Id of the staff member.
     * @param password Password of the staff member's account.
     * @param firstLogin Indicates whether it's the first login for the staff member.
     */
    public Staff(String name, String email, String facultyString, String userID, String password, Boolean firstLogin) {
        super(name, email, facultyString, userID, password, firstLogin);
        this.campsInChargeID = new ArrayList<String>();
    }

    /**
     * Construct a staff member object from the csv containing the information of the staff.
     * @param csv array return the csv values representing the staff member
     */
    public Staff(String[] csv) {
        super(csv);
        this.campsInChargeID = CSVStringHelper.CSVStringtoArraylistString(csv[6]);
        this.csvpad = csv[7];
    }
    
    
    /** 
     * @return String[]
     */
    @Override
    public String[] toSaveString() {
        String[] s = new String[8];
        String[] u = super.toSaveString();
        for (int i = 0; i < 6; i++) {
            s[i] = u[i];
        }
        s[6] = CSVStringHelper.arraylistStringtoCSVString(this.campsInChargeID);
        s[7] = this.csvpad;
        return s;
    }

    /**
     * Gets the list of camp IDs that the staff member is in charge of.
     * @return ArrayList<Staff> return the list of camp IDs the staff member is in charge of.
     */
    public ArrayList<String> getCampsInChargeID() {
        return campsInChargeID;
    }

    /**
     * Sets the list of camp IDs that the staff member is in charge of.
     * @param campsInChargeID set the list of camp IDs the staff memeber is in charge of.
     */
    public void setCampsInCharge(ArrayList<String> campsInChargeID) {
        this.campsInChargeID = campsInChargeID;
    }

    public void createCamp(String campID) {
        this.campsInChargeID.add(campID);
    }

    public void deleteCamp(String campID) {
        this.campsInChargeID.remove(campID);
    }
}
