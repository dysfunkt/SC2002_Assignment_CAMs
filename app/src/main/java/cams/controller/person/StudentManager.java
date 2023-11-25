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

public class StudentManager {
    
    /** 
     * @param ID
     * @throws ModelNotFoundException
     */
    public static void addPoint(String ID) throws ModelNotFoundException{
        Student s1 = StudentRepository.getInstance().getByID(ID);
        s1.increasePoints();
    }

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

    public static void joinAsAttendee(String ID) throws ModelNotFoundException{
        CampManager.addAttendee(ID);
        ((Student)CurrentUser.get()).joinCamp(ID);
    }

    public static void joinAsCC(String ID) throws ModelNotFoundException{
        CampManager.addCommittee(ID);
        ((Student)CurrentUser.get()).joinCommittee(ID);
    }

    public static List<String> getJoinedCamps(String ID) throws ModelNotFoundException{
        return StudentRepository.getInstance().getByID(ID).getJoinedCamps();
    }

    public static Boolean leaveCamp(String ID) throws ModelNotFoundException {
        if(((Student)CurrentUser.get()).isCampCommittee()){
            if (((Student)CurrentUser.get()).getCampIDCommittingFor().equals(ID)) {
                System.out.println("You are not allowed to leave the camp you are committeeing for.");
                return false;
            }
        }
        CampManager.removeAttendee(ID);
        ((Student)CurrentUser.get()).leaveCamp(ID);
        return true;
    }
}
