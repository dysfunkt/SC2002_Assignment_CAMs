package cams.controller.account;

import cams.boundary.login.ChangePasswordUI;
import cams.controller.account.password.PasswordManager;
import cams.controller.account.user.CurrentUser;
import cams.controller.account.user.UserFinder;
import cams.model.person.Student;
import cams.model.person.User;
import cams.model.person.UserType;
import cams.util.exception.ModelNotFoundException;
import cams.util.exception.PasswordIncorrectException;

/**
 * This class handles the logic and processing related to accounts.
 */
public class AccountManager {
    
    /** 
     * Handles login functionality.
     * @param userType Domain of user.
     * @param userID User ID entered by user.
     * @param password Password entered by user.
     * @return The user that matches the login credentials.
     * @throws PasswordIncorrectException if password entered is incorrect.
     * @throws ModelNotFoundException if user with user id is not found in the repository.
     */
    public static User login(UserType userType, String userID, String password) throws PasswordIncorrectException, ModelNotFoundException {
        User user = UserFinder.findUser(userID, userType);
        if(PasswordManager.checkPassword(user, password)) {
            CurrentUser.set(user);
            if (user.isFirstLogin()) new ChangePasswordUI().startMainMenu();
            return user;
        } else {
            throw new PasswordIncorrectException();
        }
    }

    /** 
     * Check if user is a camp committee member.
     * @param user User to check.
     * @return true if user is a camp committee member, false if user is not a camp committee member.
     */
    public static boolean checkCC(User user) {
        if (user instanceof Student) {
            if (((Student)user).isCampCommittee()) return true;
            else return false;
        }
        return false;
    }
}
