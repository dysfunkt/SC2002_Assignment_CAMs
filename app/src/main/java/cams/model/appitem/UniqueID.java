package cams.model.appitem;

public class UniqueID {
    private int campID;
    private int enquiryID;
    private int suggestionID;
    private static int lastAssignedSuggestionId;

    public UniqueID() {
        this.campID = 0;
        this.enquiryID = 0;
        this.suggestionID = 0;
    }
    public UniqueID(int campID, int enquiryID, int suggestionID) {
        this.campID = campID;
        this.enquiryID = enquiryID;
        this.suggestionID = suggestionID;
    }
    public UniqueID(String csv[]) {
        this.campID = Integer.valueOf(csv[0]);
        this.enquiryID = Integer.valueOf(csv[1]);
        this.suggestionID = Integer.valueOf(csv[2]);
    }
    public String[] toCsv() {
        String[] u = new String[3];
        u[0] = this.campID + "";
        u[1] = this.enquiryID + "";
        u[2] = this.suggestionID + "";
        return u;
    }

    public int getNextCampID() {
        return campID + 1;
    }
    public void incrementCampID() {
        this.campID++;
    }

    public int getNextEnquiryID() {
        return enquiryID + 1;
    }

    public void incrementEnquiryID() {
        this.enquiryID++;
    }

    public int getNextSuggestionID() {
        return suggestionID + 1;
    }

    public void incrementSuggestionID() {
        this.suggestionID++;
    }
    
    public int getSuggestionId() {
        lastAssignedSuggestionId++;
        return lastAssignedSuggestionId;
    }
}
