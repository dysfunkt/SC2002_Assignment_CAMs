package cams.object.person;

import java.util.ArrayList;

import cams.object.appitem.Camp;
import cams.util.CSVStringHelper;
import cams.util.IDHelper;

public class Staff extends User{
    private ArrayList<Integer> campsInChargeID;
    private String csvpad;
    public Staff(String name, String email, String facultyString, String userID, String password, Boolean firstLogin) {
        super(name, email, facultyString, userID, password, firstLogin);
        this.campsInChargeID = new ArrayList<Integer>();
    }
    public Staff(String[] csv) {
        super(csv);
        this.campsInChargeID = CSVStringHelper.CSVStringtoArraylistInteger(csv[6]);
        this.csvpad = csv[7];
    }
    public String[] toCsv() {
        String[] s = new String[8];
        String[] u = super.toCsv();
        for (int i = 0; i < 6; i++) {
            s[i] = u[i];
        }
        s[6] = CSVStringHelper.arraylistIntegertoCSVString(this.campsInChargeID);
        s[7] = this.csvpad;
        return s;
    }

    public ArrayList<Camp> getCampsInCharge() {
        ArrayList<Camp> c = new ArrayList<>();
        for (int i : campsInChargeID) {
            c.add(IDHelper.getCampFromID(i));
        }
        return c;
    }

    public void setCampsInCharge(ArrayList<Integer> campsInChargeID) {
        this.campsInChargeID = campsInChargeID;
    }
}
