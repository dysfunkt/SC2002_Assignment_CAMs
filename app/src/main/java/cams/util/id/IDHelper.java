package cams.util.id;

import java.util.ArrayList;
import java.util.List;

import cams.model.appitem.*;
import cams.model.camp.Camp;

/** 
 * This helper class is used to extract IDs from a list of Model objects.
 */
public class IDHelper {
    
    /** 
     * Get IDs from a list of Camp objects.
     * @param campList list of Camp objects to get IDs from.
     * @return List of IDs extracted from list.
     */
    public static List<String> extractCampIDs(List<Camp> campList) {
        List<String> campIDs = new ArrayList<>();
        for (Camp camp : campList) {
            campIDs.add(camp.getID());
        }
        return campIDs;
    }

    /** 
     * Get IDs from a list of Suggestion objects.
     * @param suggestionList list of Suggestion objects to get IDs from.
     * @return List of IDs extracted from list.
     */
    public static List<String> extractSuggestionIDs(List<Suggestion> suggestionList) {
        List<String> suggestionIDs = new ArrayList<>();
        for (Suggestion suggestion: suggestionList) {
            suggestionIDs.add(suggestion.getID());
        }
        return suggestionIDs;
    }

    /** 
     * Get IDs from a list of Enquiry objects.
     * @param enquiryList list of Enquiry objects to get IDs from.
     * @return List of IDs extracted from list.
     */
    public static List<String> extractEnquiryIDs(List<Enquiry> enquiryList) {
        List<String> enquiryIDs = new ArrayList<>();
        for (Enquiry enquiry: enquiryList) {
            enquiryIDs.add(enquiry.getID());
        }
        return enquiryIDs;
    }
}
