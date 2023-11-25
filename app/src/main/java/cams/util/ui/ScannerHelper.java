package cams.util.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import cams.model.camp.eLocation;
import cams.model.person.eFaculty;

public class ScannerHelper {

    public static Scanner instance;

    
    /** 
     * @return Scanner
     */
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

    public static int getIntegerInput(String prompt, int min) {
        while (true) {
            int val = getIntegerInput(prompt);
            if (val > min) return val;
            System.out.println("Invalid Input. Please ensure you enter a number greater than " + min);
        }
    }

    public static int getIntegerInput(String prompt, int min, int max) {
        while (true) {
            int val = getIntegerInput(prompt, min);
            if (val < max) return val;
            System.out.println("Invalid Input. Please ensure you enter a number less than " + max);
        }
    }

    public static String getIDInput(String prompt, List<String> acceptedValues, String errorMsg) {
        Set<String> unique = new HashSet<>(acceptedValues);
        while (true) {
            int val = getIntegerInput(prompt);
            if (unique.contains(val+"")||val == 0) return val+"";
            System.out.println(errorMsg);
        }
    }

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

    
    public static ArrayList<Date> getDatesInput(String prompt){
        Scanner input = getScannerInput();
        String dateString;
        ArrayList<Date> datesArray = new ArrayList<Date>();
        // Use SimpleDateFormat library to format the input date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        while(true){
            System.out.print(prompt);
            dateString = input.nextLine();

            if(dateString.equals("0")){
                break;
            }
            try{
                Date date = dateFormat.parse(dateString);
                datesArray.add(date);
                // You now have a Date object that you can work with
                System.out.println("You added the date: " + date);
            } catch (ParseException e){
                System.out.println("Enter the date");
            }
        }
        return datesArray;
    }

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
