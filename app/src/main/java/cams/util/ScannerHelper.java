package cams.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import cams.object.person.eFaculty;

public class ScannerHelper {

    // Ensures String input is an enum in eFaculty
    // Returns an eFaculty
    private static eFaculty parseFaculty(String input){
        // Check if faculty is valid
        for(eFaculty faculty : eFaculty.values()){
            if (faculty.name().equalsIgnoreCase(input)){
                return faculty;
            }
        }
        // If invalid, throw error
        throw new IllegalArgumentException("Invalid enum value: " + input);
    }

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

    public static Date getDateInput(String prompt){
        Scanner input = getScannerInput();
        String dateString;
        Date date;
        // Use SimpleDateFormat library to format the input date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        while (true) {
            System.out.print(prompt);
            try {
                // In case previous input was a primitive input
                input.nextLine();
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

    public static ArrayList<eFaculty> getEnumsInput(String prompt){
        Scanner input = getScannerInput();
        String enumInput;
        ArrayList<eFaculty> enumsArray = new ArrayList<eFaculty>();
        while(true){
            System.out.print(prompt);
            enumInput = input.nextLine();
            if(enumInput.equals("0")){
                break;
            }
            try{
                eFaculty faculty = parseFaculty(enumInput);
                enumsArray.add(faculty);
                // You now have a Date object that you can work with
                System.out.println("Attached faculty: " + faculty);
            } catch (IllegalArgumentException e){
                System.out.println(e);
            }
        }
        return enumsArray;
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
