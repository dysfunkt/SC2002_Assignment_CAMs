package cams.object;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.StringJoiner;

import cams.object.person.*;

public class Camp { 

    private String campName;
    private Date startDate;
    private Date endDate;
    private Date regCloseDate;
    private ArrayList<eFaculty> userGroup;
    private String campLocation;
    private int campTotalSlots;
    private int campCommitteeSlots;
    private String campDescription;
    private String staffInCharge;
    private ArrayList<String> listOfAttendees;
    private ArrayList<String> listOfCampCommittees;
    private ArrayList<String> leavers;
    private boolean visibility;

    public Camp(String campName, Date startDate, Date endDate, Date regCloseDate, 
                ArrayList<eFaculty> userGroup, String campLocation, int campTotalSlots, 
                int campCommitteeSlots, String campDescription, String staffInCharge, 
                ArrayList<String> listOfAttendees, ArrayList<String> listOfCampCommittees, 
                ArrayList<String> leavers, boolean visibility) {
        this.campName = campName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.regCloseDate = regCloseDate;
        this.userGroup = userGroup;
        this.campLocation = campLocation;
        this.campTotalSlots = campTotalSlots;
        this.campCommitteeSlots = campCommitteeSlots;
        this.campDescription = campDescription;
        this.staffInCharge = staffInCharge;
        this.listOfAttendees = listOfAttendees;
        this.listOfCampCommittees = listOfCampCommittees;
        this.leavers = leavers;
        this.visibility = visibility;
    }

    public Camp(String campName, Date startDate, Date endDate, Date regCloseDate, 
                ArrayList<eFaculty> userGroup, String campLocation, int campTotalSlots, 
                int campCommitteeSlots, String campDescription, String staffInCharge, 
                boolean visibility) {
        this.campName = campName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.regCloseDate = regCloseDate;
        this.userGroup = userGroup;
        this.campLocation = campLocation;
        this.campTotalSlots = campTotalSlots;
        this.campCommitteeSlots = campCommitteeSlots;
        this.campDescription = campDescription;
        this.staffInCharge = staffInCharge;
        this.listOfAttendees = listOfAttendees;
        this.listOfCampCommittees = listOfCampCommittees;
        this.leavers = leavers;
        this.visibility = visibility;
    }

    public Camp(String[] csv) {
        this.campName = csv[0];
        this.startDate = CSVStringtoDate(csv[1]);
        this.endDate = CSVStringtoDate(csv[2]);
        this.regCloseDate = CSVStringtoDate(csv[3]);
        this.userGroup = CSVStringtoArraylistEnum(csv[4]);
        this.campLocation = csv[5];
        this.campTotalSlots = Integer.valueOf(csv[6]);
        this.campCommitteeSlots = Integer.valueOf(csv[7]);
        this.campDescription = csv[8];
        this.staffInCharge = csv[9];
        this.listOfAttendees = CSVStringtoArraylistString(csv[10]);
        this.listOfCampCommittees = CSVStringtoArraylistString(csv[11]);
        this.leavers = CSVStringtoArraylistString(csv[12]);
        this.visibility = Boolean.valueOf(csv[13]);
    }

    public String[] toCsv() {
        String[] c = new String[14];
        c[0] = this.campName;
        c[1] = DateToCSVString(this.startDate);
        c[2] = DateToCSVString(this.endDate);
        c[3] = DateToCSVString(this.regCloseDate);
        c[4] = arraylistEnumtoCSVStrring(this.userGroup);
        c[5] = this.campLocation;
        c[6] = this.campTotalSlots + "";
        c[7] = this.campCommitteeSlots + "";
        c[8] = this.campDescription;
        c[9] = this.staffInCharge;
        c[10] = arraylistStringtoCSVString(this.listOfAttendees);
        c[11] = arraylistStringtoCSVString(this.listOfCampCommittees);
        c[12] = arraylistStringtoCSVString(this.leavers);
        c[14] = this.visibility + "";
        return c;
    }

    public String arraylistStringtoCSVString(ArrayList<String> l) {
        StringJoiner stringJoiner = new StringJoiner(",");
        for (String item : l) {
            stringJoiner.add(item);
        }
        String s = stringJoiner.toString();
        
        return s;
    }

    public ArrayList<String> CSVStringtoArraylistString(String s) {
        String[] splitArray = s.split(",");        
        ArrayList<String> l = new ArrayList<>(Arrays.asList(splitArray));

        return l;
    }

    public String arraylistEnumtoCSVStrring(ArrayList<eFaculty> u) {
        StringJoiner stringJoiner = new StringJoiner(",");
        for (eFaculty item : u) {
            stringJoiner.add(item.toString());
        }
        String s = stringJoiner.toString();

        return s;
    }

    public ArrayList<eFaculty> CSVStringtoArraylistEnum(String s) {
        String[] splitArray = s.split(",");        
        ArrayList<eFaculty> l = new ArrayList<>();
        for (String item : splitArray) {
            l.add(Enum.valueOf(eFaculty.class, item));
        }
        return l;
    }

    public Date CSVStringtoDate(String s) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String DateToCSVString(Date d) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(d);
    }

    public String getCampLocation() {
        return campLocation;
    }

    public void setCampLocation(String campLocation) {
        this.campLocation = campLocation;
    }

    public String getCampName() {
        return campName;
    }

    public void setCampName(String campName) {
        this.campName = campName;
    }

    public Date getRegCloseDate() {
        return regCloseDate;
    }

    public void setRegCloseDate(Date regCloseDate) {
        this.regCloseDate = regCloseDate;
    }

    public ArrayList<eFaculty> getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(ArrayList<eFaculty> userGroup) {
        this.userGroup = userGroup;
    }

    public int getCampTotalSlots() {
        return campTotalSlots;
    }

    public void setCampTotalSlots(int campTotalSlots) {
        this.campTotalSlots = campTotalSlots;
    }

    public int getCampCommitteeSlots() {
        return campCommitteeSlots;
    }

    public void setCampCommitteeSlots(int campCommitteeSlots) {
        this.campCommitteeSlots = campCommitteeSlots;
    }

    public String getCampDescription() {
        return campDescription;
    }

    public void setCampDescription(String campDescription) {
        this.campDescription = campDescription;
    }

    public String getStaffInCharge() {
        return staffInCharge;
    }

    public void setStaffInCharge(String staffInCharge) {
        this.staffInCharge = staffInCharge;
    }

    public ArrayList<String> getListOfAttendees() {
        return listOfAttendees;
    }

    public void setListOfAttendees(ArrayList<String> listOfAttendees) {
        this.listOfAttendees = listOfAttendees;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    /**
     * @return Date return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return Date return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return ArrayList<Student> return the listOfCampCommittees
     */
    public ArrayList<String> getListOfCampCommittees() {
        return listOfCampCommittees;
    }

    /**
     * @param listOfCampCommittees the listOfCampCommittees to set
     */
    public void setListOfCampCommittees(ArrayList<String> listOfCampCommittees) {
        this.listOfCampCommittees = listOfCampCommittees;
    }

    /**
     * @return ArrayList<Student> return the leavers
     */
    public ArrayList<String> getLeavers() {
        return leavers;
    }

    /**
     * @param leavers the leavers to set
     */
    public void setLeavers(ArrayList<String> leavers) {
        this.leavers = leavers;
    }

}