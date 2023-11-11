package cams.object.person;

import java.util.ArrayList;

import cams.object.appitem.Camp;

public class Staff extends User{
    private ArrayList<Camp> campsInCharge;
    public Staff(String name, String email, String facultyString, String userID, String password, Boolean firstLogin) {
        super(name, email, facultyString, userID, password, firstLogin);
        campsInCharge = new ArrayList<Camp>();
    }
    public Staff(String[] csv) {
        super(csv);
    }

    public ArrayList<Camp> getCampsInCharge() {
        return campsInCharge;
    }

    public void setCampsInCharge(ArrayList<Camp> campsInCharge) {
        this.campsInCharge = campsInCharge;
    }
}
