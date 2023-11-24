package cams.model.appitem;

public class Enquiry {
    private int enquiryID;
    private int campID;
    private String createdBy;
    private Boolean processed;
    private Boolean deleted;
    private String enquiryMessage;
    private String enquiryReply;
    private Boolean replyViewed; //not in use, currently use to pad the CSV so no error while reading

    public Enquiry(int enquiryID, int campID, String createdBy, Boolean processed, 
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

    public Enquiry(int enquiryID, int campID, String createdBy, String enquiryMessage) {
        this.enquiryID = enquiryID;
        this.campID = campID;
        this.createdBy = createdBy;
        this.processed = false;
        this.deleted = false;
        this.enquiryMessage = enquiryMessage;
        this.enquiryReply = "";
        this.replyViewed = false;
    }

    public Enquiry(String csv[]) {
        this.enquiryID = Integer.valueOf(csv[0]);
        this.campID = Integer.valueOf(csv[1]);
        this.createdBy = csv[2];
        this.processed = Boolean.valueOf(csv[3]);
        this.deleted = Boolean.valueOf(csv[4]);
        this.enquiryMessage = csv[5];
        this.enquiryReply = csv[6];
        this.replyViewed = Boolean.valueOf(csv[7]);
    }

    public String[] toCsv() {
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
     * @return int return the enquiryID
     */
    public int getEnquiryID() {
        return enquiryID;
    }

    /**
     * @return int return the campID
     */
    public int getCampID() {
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
     * @return Boolean return the deleted
     */
    public Boolean isReplyViewed() {
        return replyViewed;
    }
}
