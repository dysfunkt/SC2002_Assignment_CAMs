package cams.util;

import java.util.*;

public class ScannerHelper {

    public static Scanner instance;

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

    public static Scanner getScannerInput() {
        if (instance == null) instance = new Scanner(System.in);
        return instance;
    }
}
