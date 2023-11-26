package cams.boundary.login;

import java.util.Scanner;

import cams.boundary.BaseUI;
import cams.controller.account.password.PasswordManager;
import cams.controller.account.user.CurrentUser;
import cams.util.exception.PasswordIncorrectException;
import cams.util.ui.ScannerHelper;

/**
 * This cLass displays a UI for the user to change their password.
 * This class extends the BaseUI class.
 */
public class ChangePasswordUI extends BaseUI{
    
    private Scanner input = ScannerHelper.getScannerInput();

    /** 
     * Displays a UI for the user to change their password.
     * @return -1 to return to the previous menu, 1 to exit the application, otherwise 0.
     */
    protected int generateMenuScreen() {
        printHeader("Change Password");
        getPassword();
        return -1; 
    }

    /**
     * Retrieves the current password of the user.
     */
    private void getPassword(){
        System.out.println("Password must be at least 8 characters long.");
        System.out.println("Password must have at least one uppercase character, one lowercase character and one digit.");
        System.out.print("Enter old password: ");
        String oldPassword = input.nextLine();
        String newPassword = ScannerHelper.getNewPassword();
        try {
            PasswordManager.changePassword(CurrentUser.get(), oldPassword, newPassword);
        } catch (PasswordIncorrectException e) {
            System.out.println("Incorrect Password.");
            getPassword();
        }
    }
}
