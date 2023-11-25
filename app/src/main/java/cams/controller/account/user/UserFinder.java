package cams.controller.account.user;

import cams.model.person.User;
import cams.model.person.UserType;
import cams.repository.person.StaffRepository;
import cams.repository.person.StudentRepository;
import cams.util.exception.ModelNotFoundException;

/**
 * A utility class for finding users in the database.
 * Used in the login process.
 */
public class UserFinder {
    
    /**
     * Finds the student with the specified ID.
     * @param userID The ID of the student to be found.
     * @return The student with the specified ID.
     * @throws ModelNotFoundException if a student with the ID is not found in the repository.
     */
    private static User findStudent(String userID) throws ModelNotFoundException {
        return StudentRepository.getInstance().getByID(userID);
    }

    /**
     * Finds the staff with the specified ID.
     * @param userID the ID of the staff to be found.
     * @return the staff with the specified ID.
     * @throws ModelNotFoundException if a staff with the ID is not found in the repository.
     */
    private static User findStaff(String userID) throws ModelNotFoundException {
        return StaffRepository.getInstance().getByID(userID);
    }

    /**
     * Finds the user with the specified ID.
     * @param userID The ID of the user to be found.
     * @param userType The type of the user to be found.
     * @return The user with the specified ID.
     * @throws ModelNotFoundException if the user with the ID is not found in the repository.
     */
    public static User findUser(String userID, UserType userType) throws ModelNotFoundException {
        return switch (userType) {
            case STUDENT -> findStudent(userID);
            case STAFF -> findStaff(userID);
            
        };
    }
}
