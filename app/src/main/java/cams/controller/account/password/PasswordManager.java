package cams.controller.account.password;

import cams.model.person.User;
import cams.util.exception.PasswordIncorrectException;

public class PasswordManager {
    
    /** 
     * @param user
     * @param password
     * @return boolean
     */
    public static boolean checkPassword(User user, String password) {
        return user.checkPassword(password);
    }

    public static void changePassword(User user, String oldPassword, String newPassword) throws PasswordIncorrectException {
        if (checkPassword(user, oldPassword)) {
            user.changePassword(newPassword);
        } else {
            throw new PasswordIncorrectException();
        }
    }
}
