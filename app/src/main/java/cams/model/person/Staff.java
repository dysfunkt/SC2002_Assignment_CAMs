package cams.model.person;

import java.util.ArrayList;

import cams.model.appitem.Camp;
import cams.util.IDHelper;
import cams.util.iocontrol.CSVStringHelper;

public class Staff extends User{
    private ArrayList<String> campsInChargeID;
    private String csvpad;
    public Staff(String name, String email, String facultyString, String userID, String password, Boolean firstLogin) {
        super(name, email, facultyString, userID, password, firstLogin);
        this.campsInChargeID = new ArrayList<String>();
    }
    public Staff(String[] csv) {
        super(csv);
        this.campsInChargeID = CSVStringHelper.CSVStringtoArraylistString(csv[6]);
        this.csvpad = csv[7];
    }
    
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

    public ArrayList<Camp> getCampsInCharge() {
        ArrayList<Camp> c = new ArrayList<>();
        for (String i : campsInChargeID) {
            c.add(IDHelper.getCampFromID(i+""));
        }
        return c;
    }

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
