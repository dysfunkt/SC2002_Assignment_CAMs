package cams.boundary;

import java.util.Scanner;

import cams.controller.account.password.PasswordManager;
import cams.controller.account.user.CurrentUser;
import cams.util.ScannerHelper;
import cams.util.exception.PasswordIncorrectException;

public class ChangePasswordUI extends BaseUI{
    private Scanner input = ScannerHelper.getScannerInput();

    protected int generateMenuScreen() {
        printHeader("Change Password");
        input.nextLine();
        getPassword();
        return -1; 
    }

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
