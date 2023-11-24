package cams.util;
import java.util.ArrayList;

import cams.MainApp;
import cams.model.appitem.*;
import cams.model.person.*;
import cams.repository.person.StaffRepository;
import cams.repository.person.StudentRepository;

public class IDHelper {
    public static Camp getCampFromID(String ID) {
        for (Camp camp : MainApp.camps) {
            if (camp.getID().equals(ID) ) {
                return camp;
            }
        }
        System.out.println("Camp does not exist. ID: "+ ID);
        return null;
    }

    public static ArrayList<String> extractCampIDs(ArrayList<Camp> campList) {
        ArrayList<String> campIDs = new ArrayList<>();

        for (Camp camp : campList) {
            campIDs.add(camp.getID());
        }

        return campIDs;
    }

    public static ArrayList<String> extractSuggestionIDs(String campID) {
        ArrayList<String> suggestionIDs = new ArrayList<>();

        for (Suggestion suggestion: MainApp.suggestions) {
            if(suggestion.getCampID() == campID) {
                suggestionIDs.add(suggestion.getID());
            }
        }

        return suggestionIDs;
    }

    public static ArrayList<String> extractEnquiryIDs(String campID) {
        ArrayList<String> enquiryIDs = new ArrayList<>();

        for (Enquiry enquiry: MainApp.enquiries) {
            if(enquiry.getCampID() == campID) {
                enquiryIDs.add(enquiry.getID());
            }
        }

        return enquiryIDs;
    }

    public static Enquiry getEnquiryFromID(String ID) {
        for (Enquiry enquiry : MainApp.enquiries) {
            if (enquiry.getID() == ID) {
                return enquiry;
            }
        }
        System.out.println("Enquiry does not exist. ID: "+ ID);
        return null;
    }

    public static Suggestion getSuggestionFromID(String ID) {
        for (Suggestion suggestion : MainApp.suggestions) {
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
