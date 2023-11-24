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

public class EnquiryManager {

    public static void createEnquiry(String campID, String message) throws ModelAlreadyExistsException {
        Enquiry e1 = new Enquiry(UniqueIDHandler.getInstance().getNextEnquiryID(), campID, CurrentUser.get().getID(), message);
        EnquiryRepository.getInstance().add(e1);
    }

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

    public static void editEnquiry(String enquiryID, String newMessage) throws ModelNotFoundException, AlreadyProcessedException{
        Enquiry e1 = EnquiryRepository.getInstance().getByID(enquiryID);
        if (e1.isProcessed()) {
            System.out.println("You cannot edit an enquiry that has been processed.");
            throw new AlreadyProcessedException();
        }
        e1.editEnquiryMessage(newMessage);
    }

    public static List<Enquiry> getList() {
        return EnquiryRepository.getInstance().getList();
    }

    public static List<Enquiry> getListByCampID(String campID) {
        List<Enquiry> list = new ArrayList<>();
        for(Enquiry enquiry : EnquiryRepository.getInstance()) {
            if (enquiry.getCampID().equals(campID) && !enquiry.isDeleted()) {
                list.add(enquiry);
            }
        }
        return list;
    }

    public static List<Enquiry> getListByCampIDToReply(String campID) {
        List<Enquiry> list = new ArrayList<>();
        for(Enquiry enquiry : EnquiryRepository.getInstance()) {
            if (enquiry.getCampID().equals(campID) && !enquiry.isDeleted() 
                && enquiry.getCreatedBy().equals(CurrentUser.get().getID())
                && !enquiry.isProcessed()) {
                list.add(enquiry);
            }
        }
        return list;
    }

    public static List<Enquiry> getUserCreatedList() {
        List<Enquiry> list = new ArrayList<>();
        for(Enquiry enquiry : EnquiryRepository.getInstance()) {
            if (enquiry.getCreatedBy().equals(CurrentUser.get().getID())) {
                list.add(enquiry);
            }
        }
        return list;
    }

    public static List<Enquiry> getListByCampIDList(List<String> campIDList) {
        List<Enquiry>list = new ArrayList<>();
        for (String campID : campIDList) {
            list.addAll(getListByCampID(campID));
        }
        return list;
    }

    public static List<Enquiry> getUnprocessedListByCampIDList(List<String> campIDList) {
        List<Enquiry> list = getListByCampIDList(campIDList);
        List<Enquiry> unprocessed = new ArrayList<>();
        for (Enquiry enquiry : list) {
            if (enquiry.isProcessed()) {
                unprocessed.add(enquiry);
            }
        }
        return unprocessed;
    }

    public static void replyToEnquiry(String ID, String reply) throws ModelNotFoundException{
        Enquiry e1 = EnquiryRepository.getInstance().getByID(ID);
        e1.reply(reply);
        if (CurrentUser.get() instanceof Student) {
            ((Student)CurrentUser.get()).increasePoints();
        }
    }
}
