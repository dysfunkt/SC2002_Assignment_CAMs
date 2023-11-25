package cams.controller.account.password;

import cams.model.person.User;
import cams.util.exception.PasswordIncorrectException;

/**
 * This class handles the logic and processing related to passwords.
 */
public class PasswordManager {
    
    /** 
     * Check if input password matched password of user.
     * @param user User to check.
     * @param password Password input by user.
     * @return true if password is correct.
     */
    public static boolean checkPassword(User user, String password) {
        return user.checkPassword(password);
    }

    
    /** 
     * Changes the password of the user.
     * @param user The user whose password is to be changed.
     * @param oldPassword The old password.
     * @param newPassword The new password.
     * @throws PasswordIncorrectException if the old password is incorrect.
     */
    public static void changePassword(User user, String oldPassword, String newPassword) throws PasswordIncorrectException {
        if (checkPassword(user, oldPassword)) {
            user.changePassword(newPassword);
        } else {
            throw new PasswordIncorrectException();
        }
    }
}
