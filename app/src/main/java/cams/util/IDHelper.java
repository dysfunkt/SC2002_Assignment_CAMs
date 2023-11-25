package cams.util;
import java.util.ArrayList;
import java.util.List;

import cams.model.appitem.*;
import cams.model.camp.Camp;
import cams.model.person.*;
import cams.repository.appitem.CampRepository;
import cams.repository.appitem.EnquiryRepository;
import cams.repository.appitem.SuggestionRepository;
import cams.repository.person.StaffRepository;
import cams.repository.person.StudentRepository;

public class IDHelper {
    
    /** 
     * @param ID
     * @return Camp
     */
    public static Camp getCampFromID(String ID) {
        for (Camp camp : CampRepository.getInstance()) {
            if (camp.getID().equals(ID) ) {
                return camp;
            }
        }
        System.out.println("Camp does not exist. ID: "+ ID);
        return null;
    }

    public static List<String> extractCampIDs(List<Camp> campList) {
        List<String> campIDs = new ArrayList<>();

        for (Camp camp : campList) {
            campIDs.add(camp.getID());
        }

        return campIDs;
    }

    public static List<String> extractSuggestionIDs(List<Suggestion> suggestionList) {
        List<String> suggestionIDs = new ArrayList<>();

        for (Suggestion suggestion: SuggestionRepository.getInstance()) {
            suggestionIDs.add(suggestion.getID());
        }

        return suggestionIDs;
    }

    public static List<String> extractEnquiryIDs(List<Enquiry> enquiryList) {
        List<String> enquiryIDs = new ArrayList<>();

        for (Enquiry enquiry: enquiryList) {
            enquiryIDs.add(enquiry.getID());
        }

        return enquiryIDs;
    }

    public static Enquiry getEnquiryFromID(String ID) {
        for (Enquiry enquiry : EnquiryRepository.getInstance()) {
            if (enquiry.getID() == ID) {
                return enquiry;
            }
        }
        System.out.println("Enquiry does not exist. ID: "+ ID);
        return null;
    }

    public static Suggestion getSuggestionFromID(String ID) {
        for (Suggestion suggestion : SuggestionRepository.getInstance()) {
            if (suggestion.getID() == ID) {
                return suggestion;
            }
        }
        System.out.println("Suggestion does not exist. ID: "+ ID);
        return null;
    }

    public static Student getStudentFromUserID(String userID) {
        for (Student student : StudentRepository.getInstance()) {
            if (student.getID().equals(userID)) {
                return student;
            }
        }
        System.out.println("Student does not exist. ID: "+ userID);
        return null;
    }

    public static Staff getStaffFromUserID(String userID) {
        for (Staff staff : StaffRepository.getInstance()) {
            if (staff.getID() == userID) {
                return staff;
            }
        }
        System.out.println("Staff does not exist. ID: "+ userID);
        return null;
    }
}
