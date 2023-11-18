package cams.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Scanner;

import cams.MainApp;
import cams.object.appitem.Camp;
import cams.object.appitem.Enquiry;
import cams.object.appitem.Suggestion;
import cams.object.appitem.eLocation;
import cams.object.person.*;
import cams.util.CSVStringHelper;
import cams.util.ScannerHelper;

public class CCActionsMenuUI extends BaseUI{
    private Scanner input = ScannerHelper.getScannerInput();

    protected int generateMenuScreen() {
        printHeader("Camp Committee Actions Menu");
        System.out.println("1) View Registered Camp Details"); //Details of the camp they have registered for
        System.out.println("2) View Enquiries"); 
        System.out.println("3) Reply To An Enquiry");
        System.out.println("4) Submit a Suggestion");
        System.out.println("5) View My Suggestions");
        System.out.println("6) Edit a Suggestion");
        System.out.println("7) Delete a Suggestion");
        System.out.println("8) Return to Camp Committee Menu");
        System.out.println("0) Exit Application");
        printBreaks();
        int choice = doMenuChoice(8,0);
        switch (choice) {
            case 1:
                viewCampDetails();
                break;
            case 2:
                viewEnquiries();
                break;
            case 3:
                replyEnquiry();
                break;
            case 4:
                submitSuggestion();
                break;
            case 5:
                viewMySuggestions();
                break;
            case 6:
                editSuggestion();
                break;
            case 7:
                deleteSuggestion();
                break;
            case 8:
                System.out.println("Switching back to Camp Committee Menu.");
                return -1;
            case 0:
                System.out.println("Closing application...");
                return 1; //shutdown
            default:
                throw new MenuChoiceInvalidException("Camp Committee Actions Menu");
        }
        return 0;
    }


    private void viewCampDetails() {
        printHeader("View Camps");
        System.out.println("Filter by: ");
        System.out.println("1) View All");
        System.out.println("2) Faculty");
        System.out.println("3) Location");
        System.out.println("4) Camp Dates");
        System.out.println("5) Registration Date");
        System.out.println("0) Cancel");
        printBreaks();
        int choice = doMenuChoice(6, 0);
        input.nextLine();
        ArrayList<Camp> campsToDisplay = new ArrayList<>();
        switch (choice) {
            case 1:
                for (Camp camp : MainApp.camps) {
                    if((camp.getUserGroup().equals(MainApp.currentUser.getFaculty()) || camp.getUserGroup().equals(Enum.valueOf(eFaculty.class, "NTU"))) 
                        && camp.isVisibility()) {
                        campsToDisplay.add(camp);
                    }
                }
                break;
            case 2:
                int facultyFilter = ScannerHelper.getIntegerInput("Your faculty (1) or NTU (2)", 0, 3);
                switch (facultyFilter) {
                    case 1:
                        for (Camp camp : MainApp.camps) {
                            if (camp.getUserGroup().equals(MainApp.currentUser.getFaculty()) && camp.isVisibility()) 
                                campsToDisplay.add(camp);
                        }
                        break;
                    case 2:
                        for (Camp camp : MainApp.camps) {
                            if (camp.getUserGroup().equals(Enum.valueOf(eFaculty.class, "NTU")) && camp.isVisibility()) 
                                campsToDisplay.add(camp);
                        }
                        break;
                    default:
                        break;
                }
                break;
            case 3:
                eLocation locationFilter = ScannerHelper.getLocationInput();
                for (Camp camp : MainApp.camps) {
                    if ((camp.getUserGroup().equals(MainApp.currentUser.getFaculty()) || camp.getUserGroup().equals(Enum.valueOf(eFaculty.class, "NTU"))) 
                        && camp.isVisibility()
                        && camp.getCampLocation().equals(locationFilter)) {
                        campsToDisplay.add(camp);
                    }
                }
                break;
            case 4:
                Date startDate = ScannerHelper.getDateInput("From (yyyy-MM-dd): ");
                Date endDate = ScannerHelper.getDateInput("To (yyyy-MM-dd): ");
                for (Camp camp : MainApp.camps) {
                    if ((camp.getStartDate().after(startDate) || camp.getStartDate().equals(startDate)) 
                        && (camp.getEndDate().before(endDate) || camp.getEndDate().equals(endDate))
                        &&(camp.getUserGroup().equals(MainApp.currentUser.getFaculty()) || camp.getUserGroup().equals(Enum.valueOf(eFaculty.class, "NTU"))) 
                        && camp.isVisibility()) {
                        campsToDisplay.add(camp);    
                    }
                } 
                break;
            case 5: 
                Date closingDate = ScannerHelper.getDateInput("Registration closing date by (yyyy-MM-dd): ");
                for (Camp camp : MainApp.camps) {
                    if (camp.getRegCloseDate().before(closingDate) || camp.getRegCloseDate().equals(closingDate)
                        &&(camp.getUserGroup().equals(MainApp.currentUser.getFaculty()) || camp.getUserGroup().equals(Enum.valueOf(eFaculty.class, "NTU"))) 
                        && camp.isVisibility()) {
                        campsToDisplay.add(camp);
                    }
                } 
                break;
            case 0:
                return;
            default:
                break;
        }
        printBreaks();
        printListOfCamps(campsToDisplay);
    }


    private void printListOfCamps(ArrayList<Camp> list) {
        Collections.sort(list, Comparator.comparing(Camp::getCampName));
        String formatTemplate = "%-2s| %-10s| %-11s| %-11s| %-16s| %-8s| %-12s| %-16s| %-16s| %s";
        if (list.size()==0) {
            System.out.println("No Camps to Display!");
            return;
        }
        System.out.println(String.format(formatTemplate, "ID", "Camp Name", "Start Date", "End Date", "Reg. Close Date", "Faculty", "Location", "Att. slots left", "Com. slots left", "Description"));
        for (Camp camp : list) {
            System.out.println(String.format(formatTemplate, camp.getCampID(), camp.getCampName(), CSVStringHelper.DateToCSVString(camp.getStartDate()), CSVStringHelper.DateToCSVString(camp.getEndDate()), CSVStringHelper.DateToCSVString(camp.getRegCloseDate()), camp.getUserGroup() + "", camp.getCampLocation() + "", camp.remainingAttendeeSlots() + "", camp.remainingCommitteeSlots()+"", camp.getCampDescription()));
        }
    }
                
           
    public void viewEnquiries() {
        printHeader("View Enquiries");
        
        for (Enquiry enquiry : MainApp.enquiries) {
            
            // Display relevant information about each enquiry only for Camp Committee's camps
            if (((Student) MainApp.currentUser).getJoinedCamps().contains(enquiry.getCampID())) {
                System.out.println("Enquiry ID: " + enquiry.getEnquiryID());
                System.out.println("Camp ID: " + enquiry.getCampID());
                System.out.println("Created By: " + enquiry.getCreatedBy());
                System.out.println("Enquiry Message: " + enquiry.getEnquiryMessage());
                System.out.println("Processed: " + enquiry.isProcessed());
                System.out.println("Deleted: " + enquiry.isDeleted());
                System.out.println("Reply Viewed: " + enquiry.isReplyViewed());
                System.out.println("-----------------------------");
            }
        }
        printBreaks();
    }


    public void replyEnquiry() {
        printHeader("Reply to An Enquiry");
        System.out.print("Enter Enquiry ID to reply: ");
        int enquiryIDToReply = input.nextInt();
        input.nextLine(); // Consume the newline character
    
        // Find the Enquiry with the given ID
        Enquiry selectedEnquiry = null;
        for (Enquiry enquiry : MainApp.enquiries) {
            if (enquiry.getEnquiryID() == enquiryIDToReply) {
                selectedEnquiry = enquiry;
                break;
            }
        }
    
        // Check if the enquiry was found
        if (selectedEnquiry != null) {
            if (!selectedEnquiry.isProcessed()) {
                System.out.print("Enter your reply: ");
                String replyMessage = input.nextLine();
    
                // Set the reply and mark the enquiry as processed
                selectedEnquiry.reply(replyMessage);
                System.out.println("Reply sent successfully!");
            } else {
                System.out.println("Enquiry with ID " + enquiryIDToReply + " has already been processed.");
            }
        } else {
            System.out.println("Enquiry with ID " + enquiryIDToReply + " not found.");
        }
    
        printBreaks();
    }


    public void submitSuggestion() {
        printHeader("Submit a Suggestion");

        System.out.println("Available Camps: ");
        for (Camp camp : MainApp.camps) {
            System.out.println("Camp ID: " + camp.getCampID() + ", Camp Name: " + camp.getCampName());
        }

        System.out.print("Enter the Camp ID for which you want to submit a suggestion: ");
        int campID = input.nextInt();
        input.nextLine(); 

        // Check if the user is a Camp Committee member for the specified camp
        if (((Student) MainApp.currentUser).isCampCommittee() &&
            ((Student) MainApp.currentUser).getJoinedCamps().contains(campID)) {

            System.out.print("Enter your suggestion: ");
            String suggestionMessage = input.nextLine();

            int newSuggestionID = MainApp.uniqueID.getNextSuggestionID();
            
            Suggestion newSuggestion = new Suggestion(newSuggestionID, campID,
                    MainApp.currentUser.getUserID(), suggestionMessage);

            // Add the new suggestion to the list of suggestions
            MainApp.suggestions.add(newSuggestion);

            // Inform the user about the successful submission
            System.out.println("Suggestion submitted successfully!");
        } else {
            System.out.println("You are not a Camp Committee member for the specified camp.");
        }

        printBreaks();
    }


    public void viewMySuggestions() {
        printHeader("View My Suggestions");
    
        // Assuming 'suggestions' is a list containing all Suggestion objects
        for (Suggestion suggestion : MainApp.suggestions) {
            // Display relevant information about each suggestion only for the current user
            if (suggestion.getCreatedBy().equals(MainApp.currentUser.getUserID())) {
                System.out.println("Suggestion ID: " + suggestion.getSuggestionID());
                System.out.println("Camp ID: " + suggestion.getCampID());
                System.out.println("Suggestion Message: " + suggestion.getSuggestionMessage());
                System.out.println("Processed: " + suggestion.isProcessed());
                System.out.println("Deleted: " + suggestion.isDeleted());
                System.out.println("Approved: " + suggestion.isApproved());
                System.out.println("-----------------------------");
            }
        }
        printBreaks();
    }


    public void editSuggestion() {
        printHeader("Edit a Suggestion");
        System.out.print("Enter Suggestion ID to edit: ");
        int suggestionIDToEdit = input.nextInt();
        input.nextLine(); 
    
        // Find the Suggestion with the given ID
        Suggestion selectedSuggestion = findSuggestionByID(suggestionIDToEdit);
    
        // Check if the suggestion was found
        if (selectedSuggestion != null) {
            if (!selectedSuggestion.isProcessed()) {
                System.out.print("Enter the new suggestion message: ");
                String newMessage = input.nextLine();
    
                // Update the suggestion message
                selectedSuggestion.editSuggestionMessage(newMessage);
                System.out.println("Suggestion edited successfully!");
            } else {
                System.out.println("Suggestion with ID " + suggestionIDToEdit + " has already been processed.");
            }
        } else {
            System.out.println("Suggestion with ID " + suggestionIDToEdit + " not found.");
        }
    
        printBreaks();
    }
    

    public void deleteSuggestion() {
        printHeader("Delete a Suggestion");
        System.out.print("Enter Suggestion ID to delete: ");
        int suggestionIDToDelete = input.nextInt();
        input.nextLine(); 
    
        // Find the Suggestion with the given ID
        Suggestion selectedSuggestion = findSuggestionByID(suggestionIDToDelete);
    
        // Check if the suggestion was found
        if (selectedSuggestion != null) {
            // Perform the deletion
            MainApp.suggestions.remove(selectedSuggestion);
            System.out.println("Suggestion deleted successfully!");
        } else {
            System.out.println("Suggestion with ID " + suggestionIDToDelete + " not found.");
        }
    
        printBreaks();
    }
    

    private Suggestion findSuggestionByID(int suggestionID) { // Another Helper method
        for (Suggestion suggestion : MainApp.suggestions) {
            if (suggestion.getSuggestionID() == suggestionID) {
                return suggestion;
            }
        }
        return null; // If Suggestion not found
    }
}
