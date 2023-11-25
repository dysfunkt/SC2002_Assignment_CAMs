package cams.controller.account.user;

import cams.model.person.User;
import cams.model.person.UserType;
import cams.repository.person.StaffRepository;
import cams.repository.person.StudentRepository;
import cams.util.exception.ModelNotFoundException;

public class UserFinder {
    
    /** 
     * @param userID
     * @return User
     * @throws ModelNotFoundException
     */
    private static User findStudent(String userID) throws ModelNotFoundException {
        return StudentRepository.getInstance().getByID(userID);
    }

    private static User findStaff(String userID) throws ModelNotFoundException {
        return StaffRepository.getInstance().getByID(userID);
    }


    public static User findUser(String userID, UserType userType) throws ModelNotFoundException {
        return switch (userType) {
            case STUDENT -> findStudent(userID);
            case STAFF -> findStaff(userID);
            
        };
    }
}
