package cams.model.appitem;

public class Suggestion {
    private int suggestionID;
    private int campID;
    private String createdBy;
    private Boolean processed;
    private Boolean deleted;
    private String suggestionMessage;
    private Boolean approved;

    public Suggestion(int suggestionID, int campID, String createdBy, Boolean processed,
                        Boolean deleted, String suggestionMessage, Boolean approved) {
        this.suggestionID = suggestionID;
        this.campID = campID;
        this.createdBy = createdBy;
        this.processed = processed;
        this.deleted = deleted;
        this.suggestionMessage = suggestionMessage;
        this.approved = approved;
    }

    public Suggestion(int suggestionID, int campID, String createdBy, String suggestionMessage) {
        this.suggestionID = suggestionID;
        this.campID = campID;
        this.createdBy = createdBy;
        this.processed = false;
        this.deleted = false;
        this.suggestionMessage = suggestionMessage;
        this.approved = false;
    }
    
    public Suggestion(String csv[]) {
        this.suggestionID = Integer.valueOf(csv[0]);
        this.campID = Integer.valueOf(csv[1]);
        this.createdBy = csv[2];
        this.processed = Boolean.valueOf(csv[3]);
        this.deleted = Boolean.valueOf(csv[4]);
        this.suggestionMessage = csv[5];
        this.approved = Boolean.valueOf(csv[6]);
    }

    public String[] toCsv() {
        String[] s = new String[7];
        s[0] = suggestionID + "";
        s[1] = campID + "";
        s[2] = createdBy;
        s[3] = processed + "";
        s[4] = deleted + "";
        s[5] = suggestionMessage;
        s[6] = approved + "";
        return s;
    }

    /**
     * @return int return the suggestionID
     */
    public int getSuggestionID() {
        return suggestionID;
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
     * @return String return the suggestionMessage
     */
    public String getSuggestionMessage() {
        return suggestionMessage;
    }

    /**
     * @param suggestionMessage the suggestionMessage to set
     */
    public void editSuggestionMessage(String suggestionMessage) {
        this.suggestionMessage = suggestionMessage;
    }

    /**
     * @return Boolean return the approved
     */
    public Boolean isApproved() {
        return approved;
    }

    /**
     * @param approved the approved to set
     */
    public void approve(Boolean approved) {
        this.approved = true;
    }

}