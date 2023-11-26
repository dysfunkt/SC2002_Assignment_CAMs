package cams.model.camp;

import java.util.ArrayList;
import java.util.Date;

import cams.controller.account.user.CurrentUser;
import cams.model.DisplayableHeader;
import cams.model.Model;
import cams.model.person.*;
import cams.util.date.DateHandler;
import cams.util.iocontrol.CSVStringHelper;

/**
 * This class represents a camp. It contains information such as the camp name,
 * start and end dates, registration close date, group who can sign up, location, total slots, committee slots,
 * description staff, and the list of attendees, leavers and camp committee members.
 */
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

    /**
     * @param campID Id of the camp.
     * @param campName Name of the camp.
     * @param startDate Start date of the camp.
     * @param endDate End date of the camp.
     * @param regCloseDate Registration close date for the camp.
     * @param userGroup Faculty associated with the camp.
     * @param campLocation Location of the camp.
     * @param campTotalSlots Total available slots for attendees.
     * @param campCommitteeSlots Total available slots for camp committee members.
     * @param campDescription Description of the camp.
     * @param staffInCharge Staff member in charge of the camp.
     * @param listOfAttendees List of attendees user IDs.
     * @param listOfCampCommittees List of camp committee members' user IDs.
     * @param leavers List of user IDs of leavers of a camp.
     * @param visibility Visibility status of the camp.
     */
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

    /**
     * @param campID Id of the camp.
     * @param campName Name of the camp.
     * @param startDate Start date of the camp.
     * @param endDate End date of the camp.
     * @param regCloseDate Registration close date for the camp.
     * @param userGroup Faculty associated with the camp.
     * @param campLocation Location of the camp.
     * @param campTotalSlots Total available slots for attendees.
     * @param campCommitteeSlots Total available slots for camp committee members.
     * @param campDescription Description of the camp.
     * @param staffInCharge Staff member in charge of the camp.
     * @param visibility Visibility status of the camp.
     */
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

    /**
     * Constructs a camp object from the csv array
     * @param csv array containing the camp information.
     */
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

    
    /**
     * Converts the camp information to an array for saving to the csv
     * @return String[] representing the camp information to be saved.
     */
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

    /**
     * Retrieves the staff or student header string for displaying camp information depending on the user's domain.
     * Displays additional information such as visibility and remaining slots for staff only.
     * @return the formatted header string.
     */
    @Override
    public String getHeaderString(){
        if (CurrentUser.get() instanceof Staff) {
            return String.format(staffFormatTemplate, "ID", "Camp Name","Visibility", "Start Date", "End Date", "Reg. Close Date", "Faculty", "Location", "Att. slots left", "Com. slots left", "Description"); 
        } else {
            return String.format(studentFormatTemplate, "ID", "Camp Name", "Start Date", "End Date", "Reg. Close Date", "Faculty", "Location", "Att. slots left", "Com. slots left", "Description"); 

        }
    }


    /**
     * Retrieves the staff or student string representation of the camp to display depending on the user's domain.
     * Displays additional information such as visibility and remaining slots for staff only.
     * @return the formatted string representation of the camp.
     */
    @Override
    public String getDisplayableString() {
        return getSingleCampString();
    }

    /**
     * Retrieves the staff or student string representation of a camp to display depending on the user's domain.
     * Displays additional information such as visibility and remaining slots for staff only.
     * @return the formatted string representation of a single camp.
     */
    public String getSingleCampString(){
        if (CurrentUser.get() instanceof Staff) {
            return String.format(staffFormatTemplate, campID, campName, visibility, DateHandler.dateToString(startDate), DateHandler.dateToString(endDate), DateHandler.dateToString(regCloseDate), String.valueOf(userGroup), String.valueOf(campLocation), String.valueOf(remainingAttendeeSlots()), String.valueOf(remainingCommitteeSlots()), campDescription);
        } else {
            return String.format(studentFormatTemplate, campID, campName, DateHandler.dateToString(startDate), DateHandler.dateToString(endDate), DateHandler.dateToString(regCloseDate), String.valueOf(userGroup), String.valueOf(campLocation), String.valueOf(remainingAttendeeSlots()), String.valueOf(remainingCommitteeSlots()), campDescription);
        }
    }


    /**
     * Gets the location of the camp.
     *
     * @return The location of the camp.
     */
    public eLocation getCampLocation() {
        return campLocation;
    }

    /**
     * Sets the location of the camp.
     *
     * @param campLocation The new location of the camp.
     */
    public void setCampLocation(eLocation campLocation) {
        this.campLocation = campLocation;
    }

    /**
     * @return String return camp name.
     */
    public String getCampName() {
        return campName;
    }

    /**
     * @param campName set camp name.
     */
    public void setCampName(String campName) {
        this.campName = campName;
    }

    /**
     * @return Date return the registration close date.
     */
    public Date getRegCloseDate() {
        return regCloseDate;
    }

    /**
     * @param regCloseDate set registration close date.
     */
    public void setRegCloseDate(Date regCloseDate) {
        this.regCloseDate = regCloseDate;
    }

    /**
     * Retrieves the user's faculty
     * @return user's faculty
     */
    public eFaculty getUserGroup() {
        return userGroup;
    }

    /**
     * @param userGroup set the faculties eligible to participate in this camp.
     */
    public void setUserGroup(eFaculty userGroup) {
        this.userGroup = userGroup;
    }

    /**
     * Retrieves the total slots available for attendees in the camp.
     * @return Int returns the number of available slots for attendees.
     */
    public int getCampTotalSlots() {
        return campTotalSlots;
    }

    /**
     * @param campTotalSlots set the total number of slots available for attendees.
     */
    public void setCampTotalSlots(int campTotalSlots) {
        this.campTotalSlots = campTotalSlots;
    }

    /**
     * Retrieves the total camp committee slots available for the camp.
     * @return Int returns the number of available camp committee slots for the camp.
     */
    public int getCampCommitteeSlots() {
        return campCommitteeSlots;
    }

    /**
     * @param campCommitteeSlots set the total number of slots available for camp committee members.
     */
    public void setCampCommitteeSlots(int campCommitteeSlots) {
        this.campCommitteeSlots = campCommitteeSlots;
    }

    /**
     * Retrieves the description of the camp.
     * @return String return the description of the camp.
     */
    public String getCampDescription() {
        return campDescription;
    }

    /**
     * @param campDescription set the description of the camp.
     */
    public void setCampDescription(String campDescription) {
        this.campDescription = campDescription;
    }

    /**
     * Retrieves the staff in charge of the camp.
     * @return String returns the staff in charge of the camp.
     */
    public String getStaffInCharge() {
        return staffInCharge;
    }

    /**
     * @param staffInCharge set which staff is in charge of the camp.
     */
    public void setStaffInCharge(String staffInCharge) {
        this.staffInCharge = staffInCharge;
    }

    /**
     * Retrieves the list of attendees in the camp.
     * @return ArrayList<Student> return the list of attendees in the camp.
     */
    public ArrayList<String> getListOfAttendees() {
        return listOfAttendees;
    }

    /**
     * @param listOfAttendees set the list of attendees of the camp.
     */
    public void setListOfAttendees(ArrayList<String> listOfAttendees) {
        this.listOfAttendees = listOfAttendees;
    }

    /**
     * Checks if the camp is visible
     * @return True if the camp is visible, false otherwise.
     */
    public boolean isVisibility() {
        return visibility;
    }

    /**
     * Set if the camp is visible to students.
     * @param visibility set the visibility of the camp.
     */
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

    /**
     * Calculates the remaining attendee slots available for the camp.
     * @return int return the remaining slots available for the camp.
     */
    public int remainingAttendeeSlots() {
        return campTotalSlots - listOfAttendees.size() - campCommitteeSlots;
    }

    /**
     * Calculates the remaining camp committee slots available for the camp.
     * @return int return the remaining camp committee slots available for the camp.
     */
    public int remainingCommitteeSlots() {
        return campCommitteeSlots - listOfCampCommittees.size();
    }

    /**
     * Checks if there is a schedule clash for the camp and another camp the user has already registered for.
     * @param start The start date to check for clashes.
     * @param end The end date to check for clashes.
     * @return True if there is a clash, false otherwise.
     */
    public Boolean isClash(Date start, Date end) {
        return !end.before(startDate) && !start.after(endDate);
        
    }

    /**
     * Adds an attendee to the camp.
     * @param userID Id of the user to be added to camp.
     */
    public void addAttendee(String userID) {
        listOfAttendees.add(userID);
    }

    /**
     * Adds a committee member to the camp.
     * @param userID Id of the user to be added as a camp committee member.
     */
    public void addCommittee(String userID) {
        listOfCampCommittees.add(userID);
    }

    /**
     * Removes an attendee from the camp and adds them to the leavers list.
     * @param userID Id of the user to be removed from the camp and added to the leaver list.
     */
    public void removeAttendee(String userID) {
        if (listOfAttendees.contains(userID)) {
            listOfAttendees.remove(userID);
            leavers.add(userID);
        }
    }
}