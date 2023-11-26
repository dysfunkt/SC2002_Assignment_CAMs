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
     * Default constructor.
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
     * Create new camp.
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
     * Constructs a camp object from the csv array.
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
     * Converts the camp information to an array for saving to the csv.
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

    /**
     * Template for displayable camp string for staff.
     */
    private final String staffFormatTemplate = "%-3s| %-20s| %-11s| %-11s| %-11s| %-11s| %-16s| %-8s| %-12s| %-16s| %-16s| %s";

    /**
     * Template for displayable camp string for student.
     */
    private final String studentFormatTemplate = "%-3s| %-20s| %-11s| %-11s| %-16s| %-8s| %-12s| %-16s| %-16s| %s";

    /**
     * Retrieves the staff or student header string for displaying camp information depending on the user's domain.
     * Displays additional information such as visibility for staff only.
     * @return the formatted header string.
     */
    @Override
    public String getHeaderString(){
        if (CurrentUser.get() instanceof Staff) {
            return String.format(staffFormatTemplate, "ID", "Camp Name","Created By", "Visibility", "Start Date", "End Date", "Reg. Close Date", "Faculty", "Location", "Att. slots left", "Com. slots left", "Description"); 
        } else {
            return String.format(studentFormatTemplate, "ID", "Camp Name", "Start Date", "End Date", "Reg. Close Date", "Faculty", "Location", "Att. slots left", "Com. slots left", "Description"); 

        }
    }

    /**
     * Retrieves the staff or student string representation of the camp to display depending on the user's domain.
     * Displays additional information such as visibility for staff only.
     * @return the formatted string representation of the camp.
     */
    @Override
    public String getDisplayableString() {
        return getSingleCampString();
    }

    /**
     * Retrieves the staff or student string representation of a camp to display depending on the user's domain.
     * Displays additional information such as visibility for staff only.
     * @return the formatted string representation of a single camp.
     */
    public String getSingleCampString(){
        if (CurrentUser.get() instanceof Staff) {
            return String.format(staffFormatTemplate, campID, campName, staffInCharge, visibility, DateHandler.dateToString(startDate), DateHandler.dateToString(endDate), DateHandler.dateToString(regCloseDate), String.valueOf(userGroup), String.valueOf(campLocation), String.valueOf(remainingAttendeeSlots()), String.valueOf(remainingCommitteeSlots()), campDescription);
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
     * Get name of camp
     * @return Return camp name.
     */
    public String getCampName() {
        return campName;
    }

    /**
     * Set name of camp.
     * @param campName Cap name to set.
     */
    public void setCampName(String campName) {
        this.campName = campName;
    }

    /**
     * Get registration close date.
     * @return The registration close date.
     */
    public Date getRegCloseDate() {
        return regCloseDate;
    }

    /**
     * Set registration close date.
     * @param regCloseDate Registration close date to set.
     */
    public void setRegCloseDate(Date regCloseDate) {
        this.regCloseDate = regCloseDate;
    }

    /**
     * Get faculty the camp is open to.
     * @return Faculty the camp is open to.
     */
    public eFaculty getUserGroup() {
        return userGroup;
    }

    /**
     * Set faculty the camp is open to.
     * @param userGroup Set the faculty the camp is open to.
     */
    public void setUserGroup(eFaculty userGroup) {
        this.userGroup = userGroup;
    }

    /**
     * Retrieves the total slots available for participants in the camp.
     * @return Returns the number of available slots for attendees.
     */
    public int getCampTotalSlots() {
        return campTotalSlots;
    }

    /**
     * Set total slots available for participants in the camp
     * @param campTotalSlots Total number of slots available for attendees to set.
     */
    public void setCampTotalSlots(int campTotalSlots) {
        this.campTotalSlots = campTotalSlots;
    }

    /**
     * Retrieves the total camp committee slots available for the camp.
     * @return The number of available camp committee slots for the camp.
     */
    public int getCampCommitteeSlots() {
        return campCommitteeSlots;
    }

    /**
     * Srts the total camp committee slots available for the camp.
     * @param campCommitteeSlots Total number of slots available for camp committee members to set.
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
     * Set description of camp.
     * @param campDescription Description of the camp to set.
     */
    public void setCampDescription(String campDescription) {
        this.campDescription = campDescription;
    }

    /**
     * Retrieves the ID of the staff in charge of the camp.
     * @return ID of staff in charge of the camp.
     */
    public String getStaffInCharge() {
        return staffInCharge;
    }

    /**
     * Set the ID of staff in charge of the camp.
     * @param staffInCharge ID of staff in charge of the camp to set.
     */
    public void setStaffInCharge(String staffInCharge) {
        this.staffInCharge = staffInCharge;
    }

    /**
     * Retrieves the list of attendees in the camp.
     * @return List of attendees in the camp.
     */
    public ArrayList<String> getListOfAttendees() {
        return listOfAttendees;
    }

    /**
     * Set the list of attendees in the camp.
     * @param listOfAttendees List of attendees of the camp to set.
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
     * @param visibility Visibility of the camp to set.
     */
    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    /**
     * Get start date of camp.
     * @return The start date.
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Set the start date of camp.
     * @param startDate The start date to set.
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Get end date of camp.
     * @return The end date.
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Set end date of camp.
     * @param endDate The end date to set.
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Get list of ID of camp committee members.
     * @return List of ID of camp committee members.
     */
    public ArrayList<String> getListOfCampCommittees() {
        return listOfCampCommittees;
    }

    /**
     * Set list of ID of camp committee members.
     * @param listOfCampCommittees The listOfCampCommittees to set.
     */
    public void setListOfCampCommittees(ArrayList<String> listOfCampCommittees) {
        this.listOfCampCommittees = listOfCampCommittees;
    }

    /**
     * Get list of ID of students who withdrew from camp.
     * @return List of leavers.
     */
    public ArrayList<String> getLeavers() {
        return leavers;
    }

    /**
     * Set list of leavers.
     * @param leavers The list of leavers to set.
     */
    public void setLeavers(ArrayList<String> leavers) {
        this.leavers = leavers;
    }


    /**
     * Get ID of camp.
     * @return The campID.
     */
    public String getID() {
        return campID;
    }

    /**
     * Calculates the remaining attendee slots available for the camp.
     * @return The remaining attendee slots available for the camp.
     */
    public int remainingAttendeeSlots() {
        return campTotalSlots - listOfAttendees.size() - campCommitteeSlots;
    }

    /**
     * Calculates the remaining camp committee slots available for the camp.
     * @return The remaining camp committee slots available for the camp.
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
        if ((!startDate.before(start) && !startDate.after(end)) || (!endDate.before(start) && (!endDate.after(end)))) return true;
        if ((!start.before(startDate) && !start.after(endDate)) || (!end.before(startDate) && (!end.after(endDate)))) return true;
        return false;
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