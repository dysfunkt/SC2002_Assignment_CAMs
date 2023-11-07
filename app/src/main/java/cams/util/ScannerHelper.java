package cams.util;

import java.util.*;

public class ScannerHelper {

    public static Scanner instance;

    public static Scanner getScannerInput() {
        if (instance == null) instance = new Scanner(System.in);
        return instance;
    }

    public static int getIntegerInput(String prompt) {
        Scanner input = getScannerInput();
        int val;
        while (true) {
            System.out.print(prompt);
            try {
                val = input.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter numbers only.");
            } finally {
                input.nextLine();
            }
        }
        return val;
    }

    public static String getNewPassword() {
        Scanner input = getScannerInput();
        String password;
        System.out.println("Password must be at least 8 characters long.");
        System.out.println("Password must have at least one uppercase character, one lowercase character and one digit");
        boolean hasUppercase, hasLowercase, hasDigit;
        
        do {
            hasUppercase = hasLowercase = hasDigit = false;
            System.out.println("New Password: ");
            password = input.nextLine();
            if (password.length() >= 8) {
                for (char c : password.toCharArray()) {
                    if (Character.isUpperCase(c)) {
                        hasUppercase = true;
                    } else if (Character.isLowerCase(c)) {
                        hasLowercase = true;
                    } else if (Character.isDigit(c)) {
                        hasDigit = true;
                    }

                    if (hasUppercase && hasLowercase && hasDigit) {
                        break;
                    }
                }
                if (!(hasDigit && hasLowercase && hasUppercase)) System.out.println("Password must have at least one uppercase character, one lowercase character and one digit");
            } else {
                System.out.println("Password must be at least 8 characters long.");
            }
            
        } while (!(hasDigit && hasLowercase && hasUppercase));

        return password;
    }
}
