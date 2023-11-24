package cams.model.camp;

import java.util.ArrayList;
import java.util.Date;

import cams.controller.account.user.CurrentUser;
import cams.model.DisplayableHeader;
import cams.model.Model;
import cams.model.person.*;
import cams.util.date.DateHandler;
import cams.util.iocontrol.CSVStringHelper;

public class Camp implements Model, DisplayableHeader{ 

    private String campID;
    private String campName;
    private Date startDate;
    private Date endDate;
    private Date regCloseDate;
    private eFaculty userGroup;
    private eLocation campLocation;
    private int campTotalSlots;
    private int campCommitteeSlots;
    private String campDescription;
    private String staffInCharge;
    private ArrayList<String> listOfAttendees;
    private ArrayList<String> listOfCampCommittees;
    private ArrayList<String> leavers;
    private boolean visibility;

    public Camp(String campID, String campName, Date startDate, Date endDate, Date regCloseDate, 
                eFaculty userGroup, eLocation campLocation, int campTotalSlots, 
                int campCommitteeSlots, String campDescription, String staffInCharge, 
                ArrayList<String> listOfAttendees, ArrayList<String> listOfCampCommittees, 
                ArrayList<String> leavers, boolean visibility) {
        
        this.campID = campID;
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

    public Camp(String campID, String campName, Date startDate, Date endDate, Date regCloseDate, 
                eFaculty userGroup, eLocation campLocation, int campTotalSlots, 
                int campCommitteeSlots, String campDescription, String staffInCharge, 
                Boolean visibility) {
        this.campID = campID;     
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
        this.listOfAttendees = new ArrayList<String>();
        this.listOfCampCommittees = new ArrayList<String>();
        this.leavers = new ArrayList<String>();
        this.visibility = visibility;
    }

    public Camp(String[] csv) {
        this.campID = csv[0];
        this.campName = csv[1];
        this.startDate = CSVStringHelper.CSVStringtoDate(csv[2]);
        this.endDate = CSVStringHelper.CSVStringtoDate(csv[3]);
        this.regCloseDate = CSVStringHelper.CSVStringtoDate(csv[4]);
        this.userGroup = Enum.valueOf(eFaculty.class, csv[5]);
        this.campLocation = Enum.valueOf(eLocation.class, csv[6]);
        this.campTotalSlots = Integer.valueOf(csv[7]);
        this.campCommitteeSlots = Integer.valueOf(csv[8]);
        this.campDescription = csv[9];
        this.staffInCharge = csv[10];
        this.listOfAttendees = CSVStringHelper.CSVStringtoArraylistString(csv[11]);
        this.listOfCampCommittees = CSVStringHelper.CSVStringtoArraylistString(csv[12]);
        this.leavers = CSVStringHelper.CSVStringtoArraylistString(csv[13]);
        this.visibility = Boolean.valueOf(csv[14]);
    }

    public String[] toSaveString() {
        String[] c = new String[15];
        c[0] = this.campID;
        c[1] = this.campName;
        c[2] = CSVStringHelper.DateToCSVString(this.startDate);
        c[3] = CSVStringHelper.DateToCSVString(this.endDate);
        c[4] = CSVStringHelper.DateToCSVString(this.regCloseDate);
        c[5] = this.userGroup + "";
        c[6] = this.campLocation + "";
        c[7] = this.campTotalSlots + "";
        c[8] = this.campCommitteeSlots + "";
        c[9] = this.campDescription;
        c[10] = this.staffInCharge;
        c[11] = CSVStringHelper.arraylistStringtoCSVString(this.listOfAttendees);
        c[12] = CSVStringHelper.arraylistStringtoCSVString(this.listOfCampCommittees);
        c[13] = CSVStringHelper.arraylistStringtoCSVString(this.leavers);
        c[14] = this.visibility + "";
        return c;
    }

    private final String staffFormatTemplate = "%-3s| %-20s| %-11s| %-11s| %-11s| %-16s| %-8s| %-12s| %-16s| %-16s| %s";
    private final String studentFormatTemplate = "%-3s| %-20s| %-11s| %-11s| %-16s| %-8s| %-12s| %-16s| %-16s| %s";

    @Override
    public String getHeaderString(){
        if (CurrentUser.get() instanceof Staff) {
            return String.format(staffFormatTemplate, "ID", "Camp Name","Visibility", "Start Date", "End Date", "Reg. Close Date", "Faculty", "Location", "Att. slots left", "Com. slots left", "Description"); 
        } else {
            return String.format(studentFormatTemplate, "ID", "Camp Name", "Start Date", "End Date", "Reg. Close Date", "Faculty", "Location", "Att. slots left", "Com. slots left", "Description"); 

        }
    }


    @Override
    public String getDisplayableString() {
        return getSingleCampString();
    }

    public String getSingleCampString(){
        if (CurrentUser.get() instanceof Staff) {
            return String.format(staffFormatTemplate, campID, campName, visibility, DateHandler.dateToString(startDate), DateHandler.dateToString(endDate), DateHandler.dateToString(regCloseDate), String.valueOf(userGroup), String.valueOf(campLocation), String.valueOf(remainingAttendeeSlots()), String.valueOf(remainingCommitteeSlots()), campDescription);
        } else {
            return String.format(studentFormatTemplate, campID, campName, DateHandler.dateToString(startDate), DateHandler.dateToString(endDate), DateHandler.dateToString(regCloseDate), String.valueOf(userGroup), String.valueOf(campLocation), String.valueOf(remainingAttendeeSlots()), String.valueOf(remainingCommitteeSlots()), campDescription);
        }
    }



    public eLocation getCampLocation() {
        return campLocation;
    }

    public void setCampLocation(eLocation campLocation) {
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

    public eFaculty getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(eFaculty userGroup) {
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


    /**
     * @return int return the campID
     */
    public String getID() {
        return campID;
    }

    public int remainingAttendeeSlots() {
        return campTotalSlots - listOfAttendees.size() - campCommitteeSlots;
    }

    public int remainingCommitteeSlots() {
        return campCommitteeSlots - listOfCampCommittees.size();
    }

    public Boolean isClash(Date start, Date end) {
        return !end.before(startDate) && !start.after(endDate);
        
    }

    public void addAttendee(String userID) {
        listOfAttendees.add(userID);
    }

    public void addCommittee(String userID) {
        listOfCampCommittees.add(userID);
    }

    public void removeAttendee(String userID) {
        if (listOfAttendees.contains(userID)) {
            listOfAttendees.remove(userID);
            leavers.add(userID);
        }
    }
}