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
import cams.model.person.Student;
import cams.repository.appitem.CampRepository;
import cams.util.exception.ModelAlreadyExistsException;
import cams.util.exception.ModelNotFoundException;
import cams.util.exception.OperationCancelledException;
import cams.util.exception.ParticipantAlreadyRegisteredException;
import cams.util.ui.ScannerHelper;

/**
 * This class handles the logic and processing related to camps.
 */
public class CampManager {

    /** 
     * Creates a camp and adds it to the repository.
     * @param campName Name of camp.
     * @param startDate Start date of camp.
     * @param endDate End date of camp.
     * @param regCloseDate Registration closing date of camp.
     * @param userGroup Faculty camp is open to.
     * @param location Location of camps.
     * @param totalSlots Number of participants allowed.
     * @param CCSlots Number of camp committee members allowed.
     * @param description Description of camp.
     * @param visibility Visibility of camp.
     * @throws ModelAlreadyExistsException if a camp with the same ID is already in the repository.
     */
    public static void createCamp(String campName, Date startDate, Date endDate, Date regCloseDate, eFaculty userGroup, eLocation location, int totalSlots, int CCSlots, String description, Boolean visibility) throws ModelAlreadyExistsException{
        Camp c1 = new Camp(UniqueIDHandler.getInstance().getNextCampID(), campName, startDate, endDate, regCloseDate, userGroup, location, totalSlots, CCSlots, description, CurrentUser.get().getID(), visibility);
        CampRepository.getInstance().add(c1);
        ((Staff)CurrentUser.get()).createCamp(c1.getID());
    }

    /** 
     * Deletes a camp in the repository.
     * Will ask for confirmation.
     * Users cannot delete a camp if there are already participants registered for the camp.
     * @param campID ID of the camp to delete.
     * @throws ParticipantAlreadyRegisteredException if there are already participants registered for the camp.
     * @throws ModelNotFoundException if camp with ID cannot be found in the repository.
     * @throws OperationCancelledException if user cancels the operation.
     */
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

    /** 
     * Gets a list of camps from the repository with no filter.
     * @return Return all camps if current user is a staff. Return camps that are visible and in the users faculty or NTU if user is a student.
     */
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

    /** 
     * Gets a list of camps from the repository with no filter.
     * Excludes camp that the student is a committee member for.
     * Used to retrieve list of camps a camp committee member can submit enquiries for.
     * @return Return camps that are visible and in the users faculty or NTU if user is a student.
     */
    public static List<Camp> getListByFilterExcludeSelf() {
        List<Camp> list = new ArrayList<>();
        for (Camp c : CampRepository.getInstance()) {
            if((c.getUserGroup().equals(CurrentUser.get().getFaculty()) || c.getUserGroup().equals(Enum.valueOf(eFaculty.class, "NTU")))
                && c.isVisibility() && !c.getID().equals(((Student)CurrentUser.get()).getCampIDCommittingFor())) {
                    list.add(c);
                }
        }
        return list;
    }
    
    /** 
     * Gets a list of camps from the repository filtered by faculty.
     * @param filter Faculty of camp.
     * @return Return all camps in the faculty if current user is a staff. Return camps that are visible and in the faculty if user is a student.
     */
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

    /** 
     * Gets a list of camps from the repository filtered by location.
     * @param filter Location of camp.
     * @return Return all camps in the location if current user is a staff. Return camps that are visible and in the users faculty or NTU if user is a student.
     */
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
    
    /** 
     * Gets a list of camps from the repository filtered by date.
     * @param start Start date.
     * @param end End date.
     * @return Return all camps within the dates if current user is a staff. Return camps that are within the dates and in the users faculty or NTU if user is a student.
     */
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

    /** 
     * Gets a list of camps from the repository filtered by registration closing date.
     * @param regBy Registration closing date.
     * @return Return all camps that closes before the specified date if current user is a staff. Return camps that that closes before the specified date and in the users faculty or NTU if user is a student.
     */
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
    
    /** 
     * Edit name of camp.
     * @param ID ID of camp to edit.
     * @param name New name.
     * @throws ModelNotFoundException if camp with ID is not found in the repository.
     */
    public static void editName(String ID, String name) throws ModelNotFoundException{
        Camp c1 = CampRepository.getInstance().getByID(ID);
        c1.setCampName(name);
    }

    /** 
     * Edit start date of camp.
     * @param ID ID of camp to edit.
     * @param start New start date.
     * @throws ModelNotFoundException if camp with ID is not found in the repository.
     */
    public static void editStartDate(String ID, Date start) throws ModelNotFoundException{
        Camp c1 = CampRepository.getInstance().getByID(ID);
        c1.setStartDate(start);
    }

    /** 
     * Edit end date of camp.
     * @param ID ID of camp to edit.
     * @param end New end date.
     * @throws ModelNotFoundException if camp with ID is not found in the repository.
     */
    public static void editEndDate(String ID, Date end) throws ModelNotFoundException{
        Camp c1 = CampRepository.getInstance().getByID(ID);
        c1.setEndDate(end);;
    }

    /** 
     * Edit registration close date of camp.
     * @param ID ID of camp to edit.
     * @param regCloseBy New registration close date.
     * @throws ModelNotFoundException if camp with ID is not found in the repository.
     */
    public static void editRegCloseDate(String ID, Date regCloseBy) throws ModelNotFoundException{
        Camp c1 = CampRepository.getInstance().getByID(ID);
        c1.setRegCloseDate(regCloseBy);
    }

    /** 
     * Edit faculty of camp.
     * @param ID ID of camp to edit.
     * @param faculty New faculty.
     * @throws ModelNotFoundException if camp with ID is not found in the repository.
     */
    public static void editFaculty(String ID, eFaculty faculty) throws ModelNotFoundException{
        Camp c1 = CampRepository.getInstance().getByID(ID);
        c1.setUserGroup(faculty);
    }

    /** 
     * Edit location of camp.
     * @param ID ID of camp to edit.
     * @param location New location.
     * @throws ModelNotFoundException if camp with ID is not found in the repository.
     */
    public static void editLocation(String ID, eLocation location) throws ModelNotFoundException{
        Camp c1 = CampRepository.getInstance().getByID(ID);
        c1.setCampLocation(location);
    }

    /** 
     * Edit total participant slots of camp.
     * @param ID ID of camp to edit.
     * @param slots New number of slots.
     * @throws ModelNotFoundException if camp with ID is not found in the repository.
     */
    public static void editTotalSlots(String ID, int slots) throws ModelNotFoundException{
        Camp c1 = CampRepository.getInstance().getByID(ID);
        c1.setCampTotalSlots(slots);
    }

    /** 
     * Edit total camp committee slots of camp.
     * @param ID ID of camp to edit.
     * @param slots New number of slots.
     * @throws ModelNotFoundException if camp with ID is not found in the repository.
     */
    public static void editCCSlots(String ID, int slots) throws ModelNotFoundException{
        Camp c1 = CampRepository.getInstance().getByID(ID);
        c1.setCampCommitteeSlots(slots);
    }

    /** 
     * Edit description of camp.
     * @param ID ID of camp to edit.
     * @param decription New description.
     * @throws ModelNotFoundException if camp with ID is not found in the repository.
     */
    public static void editDescription(String ID, String decription) throws ModelNotFoundException{
        Camp c1 = CampRepository.getInstance().getByID(ID);
        c1.setCampDescription(decription);
    }

    /** 
     * Edit visibility of camp.
     * Cannot make invisible if camp already has participants.
     * @param ID ID of camp to edit.
     * @param visibility New visibility.
     * @throws ModelNotFoundException if camp with ID is not found in the repository.
     * @throws ParticipantAlreadyRegisteredException if camp already has registered participants.
     */
    public static void editVisibility(String ID, boolean visibility) throws ModelNotFoundException, ParticipantAlreadyRegisteredException{
        Camp c1 = CampRepository.getInstance().getByID(ID);
        if (!visibility) {
            if (c1.getListOfAttendees().size() != 0 || c1.getListOfCampCommittees().size() != 0) {
                throw new ParticipantAlreadyRegisteredException();
            }
            c1.setVisibility(visibility);
        }
    }

    /** 
     * Get remaining camp committee slots of a camp.
     * @param ID ID of camp.
     * @return remaining camp committee slots
     * @throws ModelNotFoundException if camp with ID is not found in the repository.
     */
    public static int getRemainingCCSlots(String ID) throws ModelNotFoundException {
        Camp c1 = CampRepository.getInstance().getByID(ID);
        return c1.remainingCommitteeSlots();
    }

    /** 
     * Add student to a camp as attendee.
     * Student will be current user.
     * @param ID ID of camp.
     * @throws ModelNotFoundException if camp with ID is not found in the repository.
     */
    public static void addAttendee(String ID) throws ModelNotFoundException {
        Camp c1 = CampRepository.getInstance().getByID(ID);
        c1.addAttendee(CurrentUser.get().getID());
    }

    /** 
     * Add student to a camp as camp committee.
     * Student will be current user.
     * @param ID ID of camp.
     * @throws ModelNotFoundException if camp with ID is not found in the repository.
     */
    public static void addCommittee(String ID) throws ModelNotFoundException {
        Camp c1 = CampRepository.getInstance().getByID(ID);
        c1.addCommittee(CurrentUser.get().getID());
    }

    /** 
     * Remove student from a camp.
     * Student will be current user.
     * @param ID ID of camp.
     * @throws ModelNotFoundException if camp with ID is not found in the repository.
     */
    public static void removeAttendee(String ID) throws ModelNotFoundException {
        Camp c1 = CampRepository.getInstance().getByID(ID);
        c1.removeAttendee(CurrentUser.get().getID());
    }
}