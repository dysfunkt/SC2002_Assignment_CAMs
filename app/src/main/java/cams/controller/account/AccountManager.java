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

public class AccountManager {
    
    
    /** 
     * @param userType
     * @param userID
     * @param password
     * @return User
     * @throws PasswordIncorrectException
     * @throws ModelNotFoundException
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

    public static void changePassword(UserType userType, String userID, String oldPassword, String newPassword)
            throws PasswordIncorrectException, ModelNotFoundException {
        User user = UserFinder.findUser(userID, userType);
        PasswordManager.changePassword(user, oldPassword, newPassword);

    }

    public static boolean checkCC(User user) {
        if (user instanceof Student) {
            if (((Student)user).isCampCommittee()) return true;
            else return false;
        }
        return false;
    }
}
