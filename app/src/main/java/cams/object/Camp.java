package cams.object;

import java.util.ArrayList;
import java.util.Date;

import cams.object.person.*;


public class Camp {

    private String campName;
    private ArrayList<Date> campDates;
    private Date regCloseDate;
    private ArrayList<eFaculty> userGroup;
    private String campLocation;
    private int campTotalSlots;
    private int campCommitteeSlots;
    private String campDescription;
    private Staff staffInCharge;
    private ArrayList<Student> listOfAttendees;
    private boolean visibility;

    public Camp(String campName, ArrayList<Date> campDates, Date regCloseDate, ArrayList<eFaculty> userGroup, String campLocation, int campTotalSlots, int campCommitteeSlots, String campDescription, Staff staffInCharge, ArrayList<Student> listOfAttendees, boolean visibility) {
        this.campName = campName;
        this.campDates = campDates;
        this.regCloseDate = regCloseDate;
        this.userGroup = userGroup;
        this.campLocation = campLocation;
        this.campTotalSlots = campTotalSlots;
        this.campCommitteeSlots = campCommitteeSlots;
        this.campDescription = campDescription;
        this.staffInCharge = staffInCharge;
        this.listOfAttendees = listOfAttendees;
        this.visibility = visibility;
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

    public ArrayList<Date> getCampDates() {
        return campDates;
    }

    public void setCampDates(ArrayList<Date> campDates) {
        this.campDates = campDates;
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

    public Staff getStaffInCharge() {
        return staffInCharge;
    }

    public void setStaffInCharge(Staff staffInCharge) {
        this.staffInCharge = staffInCharge;
    }

    public ArrayList<Student> getListOfAttendees() {
        return listOfAttendees;
    }

    public void setListOfAttendees(ArrayList<Student> listOfAttendees) {
        this.listOfAttendees = listOfAttendees;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }
}