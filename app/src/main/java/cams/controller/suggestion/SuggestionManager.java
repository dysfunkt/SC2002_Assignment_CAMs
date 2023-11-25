package cams.controller.suggestion;

import java.util.ArrayList;
import java.util.List;

import cams.controller.account.user.CurrentUser;
import cams.controller.person.StudentManager;
import cams.controller.repository.UniqueIDHandler;
import cams.model.appitem.Suggestion;
import cams.model.person.Student;
import cams.repository.appitem.SuggestionRepository;
import cams.util.exception.AlreadyProcessedException;
import cams.util.exception.ModelAlreadyExistsException;
import cams.util.exception.ModelNotFoundException;
import cams.util.exception.OperationCancelledException;
import cams.util.ui.ScannerHelper;

/**
 * This class handles the logic and processing related to suggestions.
 */
public class SuggestionManager {

    /** 
     * Creates a suggestion and adds it to the repository.
     * @param message Suggestion message.
     * @throws ModelAlreadyExistsException if a suggestion with the same ID is already in the repository.
     */
    public static void createSuggestion(String message) throws ModelAlreadyExistsException {
        Suggestion s1 = new Suggestion(UniqueIDHandler.getInstance().getNextSuggestionID(), ((Student)CurrentUser.get()).getCampIDCommittingFor(), CurrentUser.get().getID(), message);
        SuggestionRepository.getInstance().add(s1);
    }

    /** 
     * Deletes a suggestion in the repository.
     * Will ask for confirmation.
     * Users cannot delete a suggestion if it has been processed.
     * @param suggestionID ID of the suggestion to delete.
     * @throws ModelNotFoundException if suggestion cannot be found in the repository.
     * @throws OperationCancelledException if user chooses to cancel operation.
     * @throws AlreadyProcessedException if the suggestion has already been processed.
     */
    public static void deleteSuggestion(String suggestionID) throws ModelNotFoundException, OperationCancelledException , AlreadyProcessedException {
        Suggestion s1 = SuggestionRepository.getInstance().getByID(suggestionID);
        if (s1.isProcessed()) {
            System.out.println("You cannot delete an suggestion that has been processed.");
            throw new AlreadyProcessedException();
        }
        Boolean confirm = ScannerHelper.getYesNoInput("Confirm delete?");
        if (!confirm) {
            throw new OperationCancelledException();
        }
        SuggestionRepository.getInstance().remove(suggestionID);
    }

    
    /** 
     * Edit suggestion message.
     * Users cannot edit a suggestion if it has been processed.
     * @param suggestionID ID of the suggestion to edit.
     * @param newMessage The new suggestion message.
     * @throws ModelNotFoundException when suggestion with ID cannot be found in the repository.
     * @throws AlreadyProcessedException when suggestion has already been processed.
     */
    public static void editSuggestion(String suggestionID, String newMessage) throws ModelNotFoundException, AlreadyProcessedException {
        Suggestion s1 = SuggestionRepository.getInstance().getByID(suggestionID);
        if (s1.isProcessed()) {
            System.out.println("You cannot edit an suggestion that has been processed.");
            throw new AlreadyProcessedException();
        }
        s1.editSuggestionMessage(newMessage);
    }

    /** 
     * Gets a list of all the suggestions in the repository.
     * @return list of suggestions in the repository.
     */
    public static List<Suggestion> getList() {
        return SuggestionRepository.getInstance().getList();
    }

    /** 
     * Gets a list of suggestions of a specific camp.
     * @param campID ID of camp.
     * @return List of suggestions of the specified camp.
     */
    public static List<Suggestion> getListByCampID(String campID) {
        List<Suggestion> list = new ArrayList<>();
        for(Suggestion suggestion : SuggestionRepository.getInstance()) {
            if (suggestion.getCampID().equals(campID) && !suggestion.isDeleted()) {
                list.add(suggestion);
            }
        }
        return list;
    }

    /** 
     * Gets a list of suggestions created by a user.
     * @param userID ID of the user.
     * @return list of suggestions made by the specified user.
     */
    public static List<Suggestion> getListByUserID(String userID) {
        List<Suggestion> list = new ArrayList<>();
        for(Suggestion suggestion : SuggestionRepository.getInstance()) {
            if (suggestion.getCreatedBy().equals(userID) && !suggestion.isDeleted()) {
                list.add(suggestion);
            }
        }
        return list;
    }

    /** 
     * Gets a list of suggestions of a list of camps.
     * @param campIDList List of camps to retrieve suggestions for.
     * @return List of suggestions retrieved.
     */
    public static List<Suggestion> getListByCampIDList(List<String> campIDList) {
        List<Suggestion>list = new ArrayList<>();
        for (String campID : campIDList) {
            list.addAll(getListByCampID(campID));
        }
        return list;
    }

    /** 
     * Gets a list of unprocessed suggestions from a list of camps.
     * @param campIDList
     * @return List<Suggestion>
     */
    public static List<Suggestion> getUnprocessedListByCampIDList(List<String> campIDList) {
        List<Suggestion> list = getListByCampIDList(campIDList);
        List<Suggestion> unprocessed = new ArrayList<>();
        for (Suggestion suggestion : list) {
            if (suggestion.isProcessed()) {
                unprocessed.add(suggestion);
            }
        }
        return unprocessed;
    }

    /** 
     * Approve a suggestion.
     * @param ID ID of suggestion to approve.
     * @throws ModelNotFoundException when suggestion with ID not found in the repository.
     */
    public static void approveSuggestion(String ID) throws ModelNotFoundException{
        Suggestion s1 = SuggestionRepository.getInstance().getByID(ID);
        s1.approve();
        StudentManager.addPoint(s1.getCreatedBy());
    }

    
    
    /** 
     * Reject a suggestion.
     * @param ID ID of suggestion to reject.
     * @throws ModelNotFoundException when suggestion with ID not found in the repository.
     */
    public static void rejectSuggestion(String ID) throws ModelNotFoundException{
        Suggestion s1 = SuggestionRepository.getInstance().getByID(ID);
        s1.reject();
    }
}
