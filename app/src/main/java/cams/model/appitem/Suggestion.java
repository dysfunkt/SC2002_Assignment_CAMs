package cams.model.appitem;

import cams.model.DisplayableSplitter;
import cams.model.Model;

/**
 * This class represents a suggestion made by a user for a camp.
 * An suggestion can be created, viewed, edited, approved and deleted.
 * The class also provides methods to access and manipulate attributes of the enquiries.
 */
public class Suggestion implements Model, DisplayableSplitter{
    private String suggestionID;
    private String campID;
    private String createdBy;
    private Boolean processed;
    private Boolean deleted;
    private String suggestionMessage;
    private Boolean approved;

    /**
     * Default constructor.
     * @param suggestionID Id of the suggestion.
     * @param campID Id of the camp associated with the suggestion.
     * @param createdBy Id of the user the suggestion is associated with.
     * @param processed True if the suggestion has been processed, false otherwise.
     * @param deleted True if the suggestion has been deleted, false otherwise.
     * @param suggestionMessage The content of the suggestion message.
     * @param approved True if the suggestion has been approved, false otherwise.
     */
    public Suggestion(String suggestionID, String campID, String createdBy, Boolean processed,
                        Boolean deleted, String suggestionMessage, Boolean approved) {
        this.suggestionID = suggestionID;
        this.campID = campID;
        this.createdBy = createdBy;
        this.processed = processed;
        this.deleted = deleted;
        this.suggestionMessage = suggestionMessage;
        this.approved = approved;
    }

    /**
     * Create new suggestion.
     * @param suggestionID Id of the suggestion.
     * @param campID Id of the camp associated with the suggestion.
     * @param createdBy Id of the user the suggestion is associated with.
     * @param suggestionMessage The content of the suggestion message.
     */
    public Suggestion(String suggestionID, String campID, String createdBy, String suggestionMessage) {
        this.suggestionID = suggestionID;
        this.campID = campID;
        this.createdBy = createdBy;
        this.processed = false;
        this.deleted = false;
        this.suggestionMessage = suggestionMessage;
        this.approved = false;
    }

    /**
     * Constructs a Suggestion from the csv array.
     * @param csv array containing information about the suggestion
     */
    public Suggestion(String csv[]) {
        this.suggestionID = csv[0];
        this.campID = csv[1];
        this.createdBy = csv[2];
        this.processed = Boolean.valueOf(csv[3]);
        this.deleted = Boolean.valueOf(csv[4]);
        this.suggestionMessage = csv[5];
        this.approved = Boolean.valueOf(csv[6]);
    }


    /**
     * Converts the suggestion submitted by user to an array for saving to the csv
     * @return String[] representing the suggestion data to be saved.
     */
    public String[] toSaveString() {
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
     * Get suggestion ID.
     * @return The suggestionID.
     */
    public String getID() {
        return suggestionID;
    }

    /**
     * Get ID of camp the suggestion id for.
     * @return The campID.
     */
    public String getCampID() {
        return campID;
    }

    /**
     * Get ID of user that created the suggestion.
     * @return The user ID.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Get processed boolean.
     * @return True if processed, false if not.
     */
    public Boolean isProcessed() {
        return processed;
    }

    /**
     * Get deleted boolean.
     * @return True if deleted, false if not.
     */
    public Boolean isDeleted() {
        return deleted;
    }

    /**
     * Set deleted boolean.
     * @param deleted The deleted to set.
     */
    public void delete(Boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * Get suggestion message.
     * @return The suggestion message.
     */
    public String getSuggestionMessage() {
        return suggestionMessage;
    }

    /**
     * Edit the suggestion message.
     * @param suggestionMessage The suggestion message to set
     */
    public void editSuggestionMessage(String suggestionMessage) {
        this.suggestionMessage = suggestionMessage;
    }

    /**
     * Check if suggestion is approved.
     * @return true if approved, false if not.
     */
    public Boolean isApproved() {
        return approved;
    }


    /**
     * Approves the suggestion and sets the 'processed' and 'approved' flags to true.
     */
    public void approve() {
        this.approved = true;
        this.processed = true;
    }

    /**
     * Rejects the suggestion and sets the 'processed' flag to true while 'approved' is set to false.
     */
    public void reject() {
        this.approved = false;
        this.processed = true;
    }

    /**
     * Template for displayable suggestion string.
     */
    private final String FORMAT_TEMPLATE = "Suggestion ID: %s\n" + 
                                            "Camp ID: %s\n" +
                                            "Created By: %s\n" +
                                            "Suggestion Message: %s\n" + 
                                            "Processed: %s\n" +
                                            "Approved: %s";

    /**
     * Retrieves a formatted string representation of the suggestion.
     * @return String return formatted suggestion.
     */
    @Override
    public String getDisplayableString() {
        return getSingleSuggestionString();
    }

    /**
     * Retrieves a formatted string representation of a single suggestion.
     * @return String return a single formatted suggestion
     */
    public String getSingleSuggestionString() {
        if (isProcessed()) {
            return String.format(
                    FORMAT_TEMPLATE,
                    suggestionID,
                    campID,
                    createdBy,
                    suggestionMessage,
                    String.valueOf(isProcessed()),
                    String.valueOf(isApproved())
            );
        } else {
            return String.format(
                    FORMAT_TEMPLATE,
                    suggestionID,
                    campID,
                    createdBy,
                    suggestionMessage,
                    String.valueOf(isProcessed()),
                    "Not yet processed"
            );
}
    }


    /**
     * Retrieves a string used as a splitter between displayable items.
     * @return String return the string splitter
     */
    @Override
    public String getSplitterString() {
        return "-----------------------------";    }

}
