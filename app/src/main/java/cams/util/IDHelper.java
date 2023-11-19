package cams.util;
import java.util.ArrayList;

import cams.MainApp;
import cams.object.appitem.*;
import cams.object.person.*;

public class IDHelper {
    public static Camp getCampFromID(int ID) {
        for (Camp camp : MainApp.camps) {
            if (camp.getCampID() == ID) {
                return camp;
            }
        }
        System.out.println("Camp does not exist. ID: "+ ID);
        return null;
    }

    public static ArrayList<Integer> extractCampIDs(ArrayList<Camp> campList) {
        ArrayList<Integer> campIDs = new ArrayList<>();

        for (Camp camp : campList) {
            campIDs.add(camp.getCampID());
        }

        return campIDs;
    }

    public static ArrayList<Integer> extractSuggestionIDs(int campID) {
        ArrayList<Integer> suggestionIDs = new ArrayList<>();

        for (Suggestion suggestion: MainApp.suggestions) {
            if(suggestion.getCampID() == campID) {
                suggestionIDs.add(suggestion.getSuggestionID());
            }
        }

        return suggestionIDs;
    }

    public static ArrayList<Integer> extractEnquiryIDs(int campID) {
        ArrayList<Integer> enquiryIDs = new ArrayList<>();

        for (Enquiry enquiry: MainApp.enquiries) {
            if(enquiry.getCampID() == campID) {
                enquiryIDs.add(enquiry.getEnquiryID());
            }
        }

        return enquiryIDs;
    }

    public static Enquiry getEnquiryFromID(int ID) {
        for (Enquiry enquiry : MainApp.enquiries) {
            if (enquiry.getEnquiryID() == ID) {
                return enquiry;
            }
        }
        System.out.println("Enquiry does not exist. ID: "+ ID);
        return null;
    }

    public static Suggestion getSuggestionFromID(int ID) {
        for (Suggestion suggestion : MainApp.suggestions) {
            if (suggestion.getSuggestionID() == ID) {
                return suggestion;
            }
        }
        System.out.println("Suggestion does not exist. ID: "+ ID);
        return null;
    }

    public static Student getStudentFromUserID(String userID) {
        for (Student student : MainApp.students) {
            if (student.getUserID().equals(userID)) {
                return student;
            }
        }
        System.out.println("Student does not exist. ID: "+ userID);
        return null;
    }

    public static Staff getStaffFromUserID(String userID) {
        for (Staff staff : MainApp.staffs) {
            if (staff.getUserID() == userID) {
                return staff;
            }
        }
        System.out.println("Staff does not exist. ID: "+ userID);
        return null;
    }
}
