package cams.controller.person;

import java.util.List;

import cams.controller.account.user.CurrentUser;
import cams.controller.camp.CampManager;
import cams.model.camp.Camp;
import cams.model.person.Student;
import cams.repository.appitem.CampRepository;
import cams.repository.person.StudentRepository;
import cams.util.date.DateHandler;
import cams.util.exception.ModelNotFoundException;

/**
 * This class handles the logic and processing related to students.
 */
public class StudentManager {
    
    /** 
     * Gives a student a point.
     * @param ID ID of student.
     * @throws ModelNotFoundException if student with ID is not found in the repository.
     */
    public static void addPoint(String ID) throws ModelNotFoundException{
        Student s1 = StudentRepository.getInstance().getByID(ID);
        s1.increasePoints();
    }

    /** 
     * Check if a student can join a camp.
     * Student will be the current user.
     * @param campID ID of camp to join.
     * @return true if student can join a camp.
     * @throws ModelNotFoundException if camp with id cannot be found in the repository.
     */
    public static boolean joinCampCheck(String campID) throws ModelNotFoundException {
        Camp c1 = CampRepository.getInstance().getByID(campID);
        if (((Student)CurrentUser.get()).checkJoined(campID)) {
            System.out.println("You have already registered for this camp.");
            return false;
        }
        if(DateHandler.getTodayDate().after(c1.getRegCloseDate())) {
            System.out.println("Registration for this camp has closed.");
            return false;
        }
        if (c1.getLeavers().contains(CurrentUser.get().getID())) {
            System.out.println("You withdrew from this camp before. Registering again is not allowed. Returning to Camp Menu...");
            return false;
        }
        for (String i : ((Student)CurrentUser.get()).getJoinedCamps()) {
            if (CampRepository.getInstance().getByID(i).isClash(c1.getStartDate(), c1.getEndDate()));
            System.out.println("The dates of this camp clashes with your joined camps.");
            return false;
        }
        if (c1.remainingAttendeeSlots() == 0) {
            System.out.println("There are no more available slots in the camp");
            return false;
        }
        return true;
    }

    /** 
     * Join a camp as attendee.
     * Student will be current user.
     * @param ID ID of camp to join.
     * @throws ModelNotFoundException
     */
    public static void joinAsAttendee(String ID) throws ModelNotFoundException{
        CampManager.addAttendee(ID);
        ((Student)CurrentUser.get()).joinCamp(ID);
    }

    /** 
     * Join a camp as committee member.
     * Student will be current user.
     * @param ID ID of camp to join
     * @throws ModelNotFoundException if camp with ID cannot be found.
     */
    public static void joinAsCC(String ID) throws ModelNotFoundException{
        CampManager.addCommittee(ID);
        ((Student)CurrentUser.get()).joinCommittee(ID);
    }

    /** 
     * Get list of IDs of the camps the student has joined.
     * @param ID ID of the student.
     * @return list of IDs of the camps the student has joined.
     * @throws ModelNotFoundException if student with ID cannot be found.
     */
    public static List<String> getJoinedCamps(String ID) throws ModelNotFoundException{
        return StudentRepository.getInstance().getByID(ID).getJoinedCamps();
    }

    /** 
     * Withdraw from a camp.
     * Camp committees will not be allowed to leave a camp they are a committee member of.
     * Student will be current user.
     * @param ID ID of camp to leave
     * @return true if withdrew successfully, false if otherwise.
     * @throws ModelNotFoundException if camp with ID cannot be found.
     */
    public static Boolean leaveCamp(String ID) throws ModelNotFoundException {
        if(((Student)CurrentUser.get()).isCampCommittee()){
            if (((Student)CurrentUser.get()).getCampIDCommittingFor().equals(ID)) {
                System.out.println("You are not allowed to leave the camp you are committee member of.");
                return false;
            }
        }
        CampManager.removeAttendee(ID);
        ((Student)CurrentUser.get()).leaveCamp(ID);
        return true;
    }
}
