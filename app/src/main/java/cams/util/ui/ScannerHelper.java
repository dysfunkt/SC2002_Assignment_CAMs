package cams.util.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import cams.model.camp.eLocation;
import cams.model.person.eFaculty;

/**
 * Helper class for Scanner inputs with exception handling and input validation.
 */
public class ScannerHelper {

    /**
     * Instance of Scanner object
     */
    public static Scanner instance;

    
    /** 
     * Returns instance of Scanner object if it exists; otherwise create one.
     * @return Scanner object.
     */
    public static Scanner getScannerInput() {
        if (instance == null) instance = new Scanner(System.in);
        return instance;
    }

    /**
     * Integer input with validation of invalid characters entered.
     *
     * @param prompt Text to prompt for the input, pass in empty string for no prompt.
     * @return Integer input value.
     */
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

    /**
     * Integer input with a minimum limit.
     *
     * @param prompt Text to prompt for the input, pass in empty string for no prompt.
     * @param min    Minimum input (exclusive).
     * @return Validated input.
     */
    public static int getIntegerInput(String prompt, int min) {
        while (true) {
            int val = getIntegerInput(prompt);
            if (val > min) return val;
            System.out.println("Invalid Input. Please ensure you enter a number greater than " + min);
        }
    }

    /**
     * Integer input with a minimum and maximum limit.
     *
     * @param prompt Text to prompt for the input, pass in empty string for no prompt.
     * @param min    Minimum input (exclusive).
     * @param max    Maximum input (exclusive).
     * @return Validated input.
     */
    public static int getIntegerInput(String prompt, int min, int max) {
        while (true) {
            int val = getIntegerInput(prompt, min);
            if (val < max) return val;
            System.out.println("Invalid Input. Please ensure you enter a number less than " + max);
        }
    }

    
    /** 
     * Integer input with a list of accepted values. 
     * Used to get ID input.
     * @param prompt Text to prompt for the input, pass in empty string for no prompt.
     * @param acceptedValues List of accepted values.
     * @param errorMsg Error Message to provide to the user.
     * @return ID String.
     */
    public static String getIDInput(String prompt, List<String> acceptedValues, String errorMsg) {
        Set<String> unique = new HashSet<>(acceptedValues);
        while (true) {
            int val = getIntegerInput(prompt);
            if (unique.contains(val+"")||val == 0) return val+"";
            System.out.println(errorMsg);
        }
    }

    /**
     * Yes/No input for boolean values.
     *
     * @param prompt Text to prompt for the input, pass in empty string for no prompt.
     * @return true if yes, false if no.
     */
    public static boolean getYesNoInput(String prompt) {
        Scanner input = getScannerInput();
        String ans;
        while (true) {
            System.out.print(prompt + " [Y]es/[N]o: ");
            try {
                ans = input.nextLine().toLowerCase();
                if (ans.charAt(0) == 'y') return true;
                else if (ans.charAt(0) == 'n') return false;
                else throw new InputMismatchException();
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input. Please answer either yes or no");
            }
        }
    }

    
    /** 
     * Date input with validation.
     * @param prompt Text to prompt for the input, pass in empty string for no prompt.
     * @return Date object.
     */
    public static Date getDateInput(String prompt){
        Scanner input = getScannerInput();
        String dateString;
        Date date;
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        while (true) {
            System.out.print(prompt);
            try {
                dateString = input.nextLine();
                date = dateFormat.parse(dateString);
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter date only.");
            } catch (ParseException e){
                System.out.println("Enter the date");
            }
        }
        return date;
    }

    /** 
     * Prints a list of faculties and get user input.
     * @return Faculty.
     */
    public static eFaculty getFacultyInput() {
        Scanner input = getScannerInput();

        System.out.println("Choose Faculty: ");
        for (eFaculty l : eFaculty.values()) {
            System.out.println(l.ordinal() + 1 + ") " + l.name());
        }

        while (true) {
            System.out.print("Enter choice: ");
            if (input.hasNextInt()) {
                int choice = input.nextInt();
                if (choice > 0 && choice <= eFaculty.values().length) {
                    return eFaculty.values()[choice - 1];
                } else {
                    System.out.println("Invalid input. Please enter a valid number.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                input.next(); // consume the invalid input
            }
        }
    }

    /** 
     * Prints a list of faculties and get user input.
     * @return Faculty.
     */
    public static eLocation getLocationInput() {
        Scanner input = getScannerInput();

        System.out.println("Choose location of camp: ");
        for (eLocation l : eLocation.values()) {
            System.out.println(l.ordinal() + 1 + ") " + l.name());
        }

        while (true) {
            System.out.print("Enter choice: ");
            if (input.hasNextInt()) {
                int choice = input.nextInt();
                if (choice > 0 && choice <= eLocation.values().length) {
                    return eLocation.values()[choice - 1];
                } else {
                    System.out.println("Invalid input. Please enter a valid number.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                input.next(); // consume the invalid input
            }
        }
    }

    /** 
     * Get new password from user. Must have uppercase, lowercase, number, and at least 8 characters long.
     * @return Password string.
     */
    public static String getNewPassword() {
        Scanner input = getScannerInput();
        String password;
        boolean hasUppercase, hasLowercase, hasDigit;
        
        do {
            hasUppercase = hasLowercase = hasDigit = false;
            System.out.print("Enter new password: ");
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
