package cams.model.appitem;

import cams.model.DisplayableSplitter;
import cams.model.Model;

/**
 * This class represents an enquiry made by a user for a camp.
 * An enquiry can be created, viewed, edited, replied to, and deleted.
 * The class also provides methods to access and manipulate attributes of the enquiries.
 */
public class Enquiry implements Model, DisplayableSplitter{
    private String enquiryID;
    private String campID;
    private String createdBy;
    private Boolean processed;
    private Boolean deleted; //not in use, delete will remove enquiry from repository.
    private String enquiryMessage;
    private String enquiryReply;
    private Boolean replyViewed; //not in use, currently use to pad the CSV so no error while reading

    /**
     * Default constructor of this class.
     * @param enquiryID Id of the enquiry.
     * @param campID Id of the camp associated with the enquiry.
     * @param createdBy Id of the user the enquiry is associated with.
     * @param processed True if the enquiry has been processed, false otherwise.
     * @param deleted True if the enquiry has been deleted, false otherwise.
     * @param enquiryMessage The content of the enquiry message.
     * @param enquiryReply The content of the enquiry reply.
     * @param replyViewed True if the reply has been viewed, false otherwise.
     */
    public Enquiry(String enquiryID, String campID, String createdBy, Boolean processed,
                    Boolean deleted, String enquiryMessage, String enquiryReply,
                    Boolean replyViewed) {
        this.enquiryID = enquiryID;
        this.campID = campID;
        this.createdBy = createdBy;
        this.processed = processed;
        this.deleted = deleted;
        this.enquiryMessage = enquiryMessage;
        this.enquiryReply = enquiryReply;
        this.replyViewed = replyViewed;
    }

    /**
     * Constructor for new enquiry.
     * @param enquiryID Id of the enquiry.
     * @param campID Id of the camp associated with the enquiry.
     * @param createdBy Id of the user the enquiry is associated with.
     * @param enquiryMessage The content of the enquiry message.
     */
    public Enquiry(String enquiryID, String campID, String createdBy, String enquiryMessage) {
        this.enquiryID = enquiryID;
        this.campID = campID;
        this.createdBy = createdBy;
        this.processed = false;
        this.deleted = false;
        this.enquiryMessage = enquiryMessage;
        this.enquiryReply = "";
        this.replyViewed = false;
    }

    /**
     * Construct class from CSV.
     * @param csv Array of string with attributes.
     */
    public Enquiry(String csv[]) {
        this.enquiryID = csv[0];
        this.campID = csv[1];
        this.createdBy = csv[2];
        this.processed = Boolean.valueOf(csv[3]);
        this.deleted = Boolean.valueOf(csv[4]);
        this.enquiryMessage = csv[5];
        this.enquiryReply = csv[6];
        this.replyViewed = Boolean.valueOf(csv[7]);
    }

    
    /**
     * Generate an array of class attributes in string format to save to CSV. 
     * @return String array of enquiry data to be saved.
     */
    public String[] toSaveString() {
        String[] e = new String[8];
        e[0] = enquiryID + "";
        e[1] = campID + "";
        e[2] = createdBy;
        e[3] = processed + "";
        e[4] = deleted + "";
        e[5] = enquiryMessage;
        e[6] = enquiryReply;
        e[7] = replyViewed + "";
        return e;
    }
    

    /**
     * Get enquiryID. 
     * @return The enquiryID
     */
    public String getID() {
        return enquiryID;
    }

    /**
     * Get ID of camp the enquiry is for.
     * @return CampID
     */
    public String getCampID() {
        return campID;
    }

    /**
     * Get ID of the user that created the enquiry.
     * @return User ID
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Get processed boolean.
     * @return True if processed, false if not processed.
     */
    public Boolean isProcessed() {
        return processed;
    }

    /**
     * Get deleted boolean.
     * @return True if deleted, false if not deleted.
     */
    public Boolean isDeleted() {
        return deleted;
    }

    /**
     * Deletes the enquiry.
     * @param deleted Set delete.
     */
    public void delete(Boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * Get enquiry message.
     * @return Enquiry message.
     */
    public String getEnquiryMessage() {
        return enquiryMessage;
    }

    /**
     * Set enquiry message.
     * @param enquiryMessage The enquiryMessage to set.
     */
    public void editEnquiryMessage(String enquiryMessage) {
        this.enquiryMessage = enquiryMessage;
    }

    /**
     * Get reply message.
     * @return The reply message.
     */
    public String viewReply() {
        return enquiryReply;
    }

    /**
     * Set reply message.
     * Will set processed to true.
     * @param enquiryReply The reply message.
     */
    public void reply(String enquiryReply) {
        this.enquiryReply = enquiryReply;
        this.processed = true;
    }

    /**
     * Checks if the reply to the enquiry has been viewed.
     * @return Boolean return True if the reply has been viewed, false otherwise.
     */
    public Boolean isReplyViewed() {
        return replyViewed;
    }

    /**
     * Template to display the enquiry information.
     */
    private final String FORMAT_TEMPLATE = "Enquiry ID: %s\n" +
                                            "Camp ID: %s\n" +
                                            "Created By: %s\n" +
                                            "Enquiry Message: %s\n" +
                                            "Processed: %s\n"+
                                            "Reply Message: %s";

    /**
     * Retrieves a formatted string representation of the enquiry.
     * @return String returns the formatted the enquiry.
     */
    @Override
    public String getDisplayableString() {
        return getSingleEnquiryString();
    }

    /**
     * Retrieves a formatted string representation of a single enquiry.
     * @return String returns the formatted a single enquiry.
     */
    public String getSingleEnquiryString() {
        return String.format(FORMAT_TEMPLATE, enquiryID, campID, createdBy, enquiryMessage, String.valueOf(isProcessed()), enquiryReply);
    }

    /**
     * Retrieves a string used as a splitter between displayable items.
     * @return String return the string splitter
     */
    @Override
    public String getSplitterString() {
        return "-----------------------------";
    }
}
