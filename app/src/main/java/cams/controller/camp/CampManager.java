package cams.controller.camp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cams.controller.account.user.CurrentUser;
import cams.controller.repository.UniqueIDHandler;
import cams.model.camp.Camp;
import cams.model.camp.eLocation;
import cams.model.person.eFaculty;
import cams.model.person.Staff;
import cams.repository.appitem.CampRepository;
import cams.util.exception.ModelAlreadyExistsException;
import cams.util.exception.ModelNotFoundException;
import cams.util.exception.OperationCancelledException;
import cams.util.exception.ParticipantAlreadyRegisteredException;
import cams.util.ui.ScannerHelper;

public class CampManager {

    public static void createCamp(String campName, Date startDate, Date endDate, Date regCloseDate, eFaculty userGroup, eLocation location, int totalSlots, int CCSlots, String description, Boolean visibility) throws ModelAlreadyExistsException{
        Camp c1 = new Camp(UniqueIDHandler.getInstance().getNextCampID(), campName, startDate, endDate, regCloseDate, userGroup, location, totalSlots, CCSlots, description, CurrentUser.get().getID(), visibility);
        CampRepository.getInstance().add(c1);
        ((Staff)CurrentUser.get()).createCamp(c1.getID());
    }

    public static void deleteCamp(String campID) throws ParticipantAlreadyRegisteredException, ModelNotFoundException, OperationCancelledException{
        Camp c1 = CampRepository.getInstance().getByID(campID);
        if (c1.getListOfAttendees().size() != 0 || c1.getListOfCampCommittees().size() != 0) {
            throw new ParticipantAlreadyRegisteredException();
        }
        Boolean confirm = ScannerHelper.getYesNoInput("Confirm delete?");
        if (!confirm) {
            throw new OperationCancelledException();
        }
        CampRepository.getInstance().remove(campID);
        ((Staff)CurrentUser.get()).deleteCamp(campID);
    }

    public static List<Camp> getListByFilter() {
        if (CurrentUser.get() instanceof Staff){
            return CampRepository.getInstance().getList();
        } else {
            List<Camp> list = new ArrayList<>();
            for (Camp c : CampRepository.getInstance()) {
                if((c.getUserGroup().equals(CurrentUser.get().getFaculty()) || c.getUserGroup().equals(Enum.valueOf(eFaculty.class, "NTU")))
                    && c.isVisibility()) {
                        list.add(c);
                    }
            }
            return list;
        }
    }

    public static List<Camp> getListByFilter(eFaculty filter) {
        List<Camp> list = new ArrayList<>();
        if (CurrentUser.get() instanceof Staff) {
            for(Camp c : CampRepository.getInstance()) {
                if (c.getUserGroup().equals(filter)) {
                    list.add(c);
                }
            }
        } else {
            for(Camp c : CampRepository.getInstance()) {
                if (c.getUserGroup().equals(filter) && c.isVisibility()) {
                    list.add(c);
                }
            }
        }
        return list;
    }

    public static List<Camp> getListByFilter(eLocation filter) {
        List<Camp> list = new ArrayList<>();
        if (CurrentUser.get() instanceof Staff) {
            for(Camp c : CampRepository.getInstance()) {
                if (c.getCampLocation().equals(filter)) {
                    list.add(c);
                }
            }
        } else {
            for (Camp c : CampRepository.getInstance()) {
                if((c.getUserGroup().equals(CurrentUser.get().getFaculty()) || c.getUserGroup().equals(Enum.valueOf(eFaculty.class, "NTU")))
                    && c.isVisibility()
                    && c.getCampLocation().equals(filter)) {
                    list.add(c);
                }
            }
        }
        return list;
    }

    public static List<Camp> getListByFilter(Date start, Date end) {
        List<Camp> list = new ArrayList<>();
        if (CurrentUser.get() instanceof Staff) {
            for(Camp c : CampRepository.getInstance()) {
                if (!c.getStartDate().before(start) && !c.getEndDate().after(end)) {
                    list.add(c);
                }
            }
        } else {
            for(Camp c : CampRepository.getInstance()) {
                if (!c.getStartDate().before(start) && !c.getEndDate().after(end)
                    && (c.getUserGroup().equals(CurrentUser.get().getFaculty()) || c.getUserGroup().equals(Enum.valueOf(eFaculty.class, "NTU")))
                    && c.isVisibility()) {
                    list.add(c);
                }
            }
        }

        return list;
    }

    public static List<Camp> getListByFilter(Date regBy) {
        List<Camp> list = new ArrayList<>();
        if (CurrentUser.get() instanceof Staff) {
            for(Camp c : CampRepository.getInstance()) {
                if (!c.getRegCloseDate().after(regBy)) {
                    list.add(c);
                }
            }
        } else {
            for(Camp c : CampRepository.getInstance()) {
                if (!c.getRegCloseDate().after(regBy)
                    && (c.getUserGroup().equals(CurrentUser.get().getFaculty()) || c.getUserGroup().equals(Enum.valueOf(eFaculty.class, "NTU")))
                    && c.isVisibility()) {
                    list.add(c);
                }
            }
        }
        return list;
    }
    
    public static void editName(String ID, String name) throws ModelNotFoundException{
        Camp c1 = CampRepository.getInstance().getByID(ID);
        c1.setCampName(name);
    }

    public static void editStartDate(String ID, Date start) throws ModelNotFoundException{
        Camp c1 = CampRepository.getInstance().getByID(ID);
        c1.setStartDate(start);
    }

    public static void editEndDate(String ID, Date end) throws ModelNotFoundException{
        Camp c1 = CampRepository.getInstance().getByID(ID);
        c1.setEndDate(end);;
    }

    public static void editRegCloseDate(String ID, Date regCloseBy) throws ModelNotFoundException{
        Camp c1 = CampRepository.getInstance().getByID(ID);
        c1.setRegCloseDate(regCloseBy);
    }

    public static void editFaculty(String ID, eFaculty faculty) throws ModelNotFoundException{
        Camp c1 = CampRepository.getInstance().getByID(ID);
        c1.setUserGroup(faculty);
    }

    public static void editLocation(String ID, eLocation location) throws ModelNotFoundException{
            Camp c1 = CampRepository.getInstance().getByID(ID);
            c1.setCampLocation(location);
        }

    public static void editTotalSlots(String ID, int slots) throws ModelNotFoundException{
            Camp c1 = CampRepository.getInstance().getByID(ID);
            c1.setCampTotalSlots(slots);
        }

    public static void editCCSlots(String ID, int slots) throws ModelNotFoundException{
        Camp c1 = CampRepository.getInstance().getByID(ID);
        c1.setCampCommitteeSlots(slots);
    }

    public static void editDescription(String ID, String decription) throws ModelNotFoundException{
        Camp c1 = CampRepository.getInstance().getByID(ID);
        c1.setCampDescription(decription);
    }

    public static void editVisibility(String ID, boolean visibility) throws ModelNotFoundException, ParticipantAlreadyRegisteredException{
        Camp c1 = CampRepository.getInstance().getByID(ID);
        if (!visibility) {
            if (c1.getListOfAttendees().size() != 0 || c1.getListOfCampCommittees().size() != 0) {
                throw new ParticipantAlreadyRegisteredException();
            }
            c1.setVisibility(visibility);
        }
    }

    public static int getRemainingCCSlots(String ID) throws ModelNotFoundException {
        Camp c1 = CampRepository.getInstance().getByID(ID);
        return c1.remainingCommitteeSlots();
    }

    public static void addAttendee(String ID) throws ModelNotFoundException {
        Camp c1 = CampRepository.getInstance().getByID(ID);
        c1.addAttendee(CurrentUser.get().getID());
    }

    public static void addCommittee(String ID) throws ModelNotFoundException {
        Camp c1 = CampRepository.getInstance().getByID(ID);
        c1.addCommittee(CurrentUser.get().getID());
    }

    public static void removeAttendee(String ID) throws ModelNotFoundException {
        Camp c1 = CampRepository.getInstance().getByID(ID);
        c1.removeAttendee(CurrentUser.get().getID());
    }
}