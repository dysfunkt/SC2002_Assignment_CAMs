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

public class SuggestionManager {

    
    /** 
     * @param message
     * @throws ModelAlreadyExistsException
     */
    public static void createSuggestion(String message) throws ModelAlreadyExistsException {
        Suggestion s1 = new Suggestion(UniqueIDHandler.getInstance().getNextSuggestionID(), ((Student)CurrentUser.get()).getCampIDCommittingFor(), CurrentUser.get().getID(), message);
        SuggestionRepository.getInstance().add(s1);
    }

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

    public static void editSuggestion(String suggestionID, String newMessage) throws ModelNotFoundException, AlreadyProcessedException {
        Suggestion s1 = SuggestionRepository.getInstance().getByID(suggestionID);
        if (s1.isProcessed()) {
            System.out.println("You cannot edit an suggestion that has been processed.");
            throw new AlreadyProcessedException();
        }
        s1.editSuggestionMessage(newMessage);
    }

    public static List<Suggestion> getList() {
        return SuggestionRepository.getInstance().getList();
    }

    public static List<Suggestion> getListByCampID(String campID) {
        List<Suggestion> list = new ArrayList<>();
        for(Suggestion suggestion : SuggestionRepository.getInstance()) {
            if (suggestion.getCampID().equals(campID) && !suggestion.isDeleted()) {
                list.add(suggestion);
            }
        }
        return list;
    }

    public static List<Suggestion> getListByUserID(String userID) {
        List<Suggestion> list = new ArrayList<>();
        for(Suggestion suggestion : SuggestionRepository.getInstance()) {
            if (suggestion.getCreatedBy().equals(userID) && !suggestion.isDeleted()) {
                list.add(suggestion);
            }
        }
        return list;
    }

    public static List<Suggestion> getListByCampIDList(List<String> campIDList) {
        List<Suggestion>list = new ArrayList<>();
        for (String campID : campIDList) {
            list.addAll(getListByCampID(campID));
        }
        return list;
    }

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

    public static void approveSuggestion(String ID) throws ModelNotFoundException{
        Suggestion s1 = SuggestionRepository.getInstance().getByID(ID);
        s1.approve();
        StudentManager.addPoint(s1.getCreatedBy());
    }

    public static void rejectSuggestion(String ID) throws ModelNotFoundException{
        Suggestion s1 = SuggestionRepository.getInstance().getByID(ID);
        s1.reject();
    }
}
