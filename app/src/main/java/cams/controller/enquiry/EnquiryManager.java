package cams.controller.enquiry;

import java.util.ArrayList;
import java.util.List;

import cams.controller.account.user.CurrentUser;
import cams.controller.repository.UniqueIDHandler;
import cams.model.appitem.Enquiry;
import cams.model.person.Student;
import cams.repository.appitem.EnquiryRepository;
import cams.util.exception.AlreadyProcessedException;
import cams.util.exception.ModelAlreadyExistsException;
import cams.util.exception.ModelNotFoundException;
import cams.util.exception.OperationCancelledException;
import cams.util.ui.ScannerHelper;

/**
 * This class handles the logic and processing related to enquiries.
 */
public class EnquiryManager {

    /** 
     * Creates an enquiry and add it to the repository.
     * @param campID ID of the camp the enquiry is regarding.
     * @param message The enquiry message.
     * @throws ModelAlreadyExistsException if camp with ID cannot be found in repository.
     */
    public static void createEnquiry(String campID, String message) throws ModelAlreadyExistsException {
        Enquiry e1 = new Enquiry(UniqueIDHandler.getInstance().getNextEnquiryID(), campID, CurrentUser.get().getID(), message);
        EnquiryRepository.getInstance().add(e1);
    }
    
    /** 
     * Deletes an enquiry from the repository.
     * Will ask the user for confirmation.
     * Users cannot delete an enquiry if it has been processed.
     * @param enquiryID ID of the enquiry.
     * @throws ModelNotFoundException if enquiry with ID cannot be found in repository.
     * @throws OperationCancelledException if operation cancelled by user.
     * @throws AlreadyProcessedException if enquiry has already been processed.
     */
    public static void deleteEnquiry(String enquiryID) throws ModelNotFoundException, OperationCancelledException , AlreadyProcessedException{
        Enquiry e1 = EnquiryRepository.getInstance().getByID(enquiryID);
        if (e1.isProcessed()) {
            System.out.println("You cannot delete an enquiry that has been processed.");
            throw new AlreadyProcessedException();
        }
        Boolean confirm = ScannerHelper.getYesNoInput("Confirm delete?");
        if (!confirm) {
            throw new OperationCancelledException();
        }
        EnquiryRepository.getInstance().remove(enquiryID);
    }

    /** 
     * Edit enquiry message.
     * Users cannot edit an enquiry if it has been processed.
     * @param enquiryID ID of enquiry to message.
     * @param newMessage The new enquiry message.
     * @throws ModelNotFoundException if enquiry cannot be found in repository.
     * @throws AlreadyProcessedException if enquiry has already been processed.
     */
    public static void editEnquiry(String enquiryID, String newMessage) throws ModelNotFoundException, AlreadyProcessedException{
        Enquiry e1 = EnquiryRepository.getInstance().getByID(enquiryID);
        if (e1.isProcessed()) {
            System.out.println("You cannot edit an enquiry that has been processed.");
            throw new AlreadyProcessedException();
        }
        e1.editEnquiryMessage(newMessage);
    }

    /** 
     * Gets a list of all the enquiries in the repository.
     * @return list of enquiries in the repository.
     */
    public static List<Enquiry> getList() {
        return EnquiryRepository.getInstance().getList();
    }

    /** 
     * Gets a list of enquiries of a specific camp.
     * @param campID ID of camp.
     * @return List of enquiries of the specified camp.
     */
    public static List<Enquiry> getListByCampID(String campID) {
        List<Enquiry> list = new ArrayList<>();
        for(Enquiry enquiry : EnquiryRepository.getInstance()) {
            if (enquiry.getCampID().equals(campID) && !enquiry.isDeleted()) {
                list.add(enquiry);
            }
        }
        return list;
    }

    /** 
     * Gets a list of enquiries of a specific camp that can be replied to.
     * @param campID ID of camp.
     * @return List of enquiries of the specified camp.
     */
    public static List<Enquiry> getListByCampIDToReply(String campID) {
        List<Enquiry> list = new ArrayList<>();
        for(Enquiry enquiry : EnquiryRepository.getInstance()) {
            if (enquiry.getCampID().equals(campID) && !enquiry.isDeleted() 
                && !enquiry.getCreatedBy().equals(CurrentUser.get().getID())
                && !enquiry.isProcessed()) {
                list.add(enquiry);
            }
        }
        return list;
    }

    /** 
     * Gets a list of enquiries created by the user.
     * @return list of enquiries created by the user.
     */
    public static List<Enquiry> getUserCreatedList() {
        List<Enquiry> list = new ArrayList<>();
        for(Enquiry enquiry : EnquiryRepository.getInstance()) {
            if (enquiry.getCreatedBy().equals(CurrentUser.get().getID())) {
                list.add(enquiry);
            }
        }
        return list;
    }

    /** 
     * Gets a list of enquiries of a list of camps.
     * @param campIDList List of ID of the camps to get enquiries from.
     * @return list of enquiries.
     */
    public static List<Enquiry> getListByCampIDList(List<String> campIDList) {
        List<Enquiry>list = new ArrayList<>();
        for (String campID : campIDList) {
            list.addAll(getListByCampID(campID));
        }
        return list;
    }

    /** 
     * Gets a list of unprocessed enquiries of a list of camps.
     * @param campIDList List of ID of the camps to get enquiries from.
     * @return list of unprocessed enquiries.
     */
    public static List<Enquiry> getUnprocessedListByCampIDList(List<String> campIDList) {
        List<Enquiry> list = getListByCampIDList(campIDList);
        List<Enquiry> unprocessed = new ArrayList<>();
        for (Enquiry enquiry : list) {
            if (!enquiry.isProcessed()) {
                unprocessed.add(enquiry);
            }
        }
        return unprocessed;
    }

    /** 
     * Reply to an enquiry.
     * @param ID ID of enquiry to reply.
     * @param reply Reply message.
     * @throws ModelNotFoundException if enquiry with ID is not found in repository.
     */
    public static void replyToEnquiry(String ID, String reply) throws ModelNotFoundException{
        Enquiry e1 = EnquiryRepository.getInstance().getByID(ID);
        e1.reply(reply);
        if (CurrentUser.get() instanceof Student) {
            ((Student)CurrentUser.get()).increasePoints();
        }
    }
}
