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
    private Boolean deleted;
    private String enquiryMessage;
    private String enquiryReply;
    private Boolean replyViewed; //not in use, currently use to pad the CSV so no error while reading

    /**
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
     * @param csv
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
     * @return String[]
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
     * @return String return the enquiryID
     */
    public String getID() {
        return enquiryID;
    }

    /**
     * @return String return the campID
     */
    public String getCampID() {
        return campID;
    }

    /**
     * @return String return the createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @return Boolean return the processed
     */
    public Boolean isProcessed() {
        return processed;
    }

    /**
     * @return Boolean return the deleted
     */
    public Boolean isDeleted() {
        return deleted;
    }

    /**
     * @param deleted the deleted to set
     */
    public void delete(Boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * @return String return the enquiryMessage
     */
    public String getEnquiryMessage() {
        return enquiryMessage;
    }

    /**
     * @param enquiryMessage the enquiryMessage to set
     */
    public void editEnquiryMessage(String enquiryMessage) {
        this.enquiryMessage = enquiryMessage;
    }

    /**
     * @return String return the enquiryReply
     */
    public String viewReply() {
        return enquiryReply;
    }

    /**
     * @param enquiryReply the enquiryReply to set
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
