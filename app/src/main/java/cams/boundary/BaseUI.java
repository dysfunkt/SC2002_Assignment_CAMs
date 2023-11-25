package cams.boundary;

import java.util.stream.Stream;

import cams.util.exception.MenuChoiceInvalidException;
import cams.util.ui.ScannerHelper;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Abstract class where all menu UIs inherits from.
 */
public abstract class BaseUI {
    /**
     * Constant for defining maximum window size for console printing.
     */
    private static final int PRINT_MAX_WINDOW_SIZE = 40;

    /**
     * An abstract class that subclasses must override and implement this method to create the menu content.
     */
    protected abstract int generateMenuScreen();

    
    /** 
     * @return boolean
     */
    public boolean startMainMenu() {
        while (true) {
            try {
                int exit = generateMenuScreen();
                if (exit < 0) return false;
                else if (exit > 0) return true;
                
            } catch (MenuChoiceInvalidException e) {
                System.out.println(e.getLocalizedMessage());
            }
        }
    }

    /**
     * Helper method to print the header of a menu.
     *
     * @param headerName The name of the menu
     */
    protected static void printHeader(String headerName) {
        printHeader(headerName, PRINT_MAX_WINDOW_SIZE);
    }

    /**
     * Helper method to print the header of a menu.
     * @param headerName Name of the menu
     * @param length Length of the memu
     */
    protected static void printHeader(String headerName, int length) {
        printBreaks(length);
        int numOfSpaces;
        if (headerName.length() > length) numOfSpaces = 0;
        else {
            numOfSpaces = (length - headerName.length()) / 2;
            Stream.generate(() -> " ").limit(numOfSpaces).forEach(System.out::print);
            System.out.println(headerName);
            printBreaks(length);
        }
    }

    /**
     * Helper method to create a menu separator.
     * @param length How many dashes to append
     */
    protected static void printBreaks(int length) {
        Stream.generate(() -> "-").limit(length).forEach((System.out::print));
        System.out.println();
    }

    /**
     * Helper method to create a menu separator.
     */
    protected static void printBreaks() {
        printBreaks(PRINT_MAX_WINDOW_SIZE);
    }

    protected static int doMenuChoice(int max, int specialEscape) {
        Scanner input = ScannerHelper.getScannerInput();
        int selection;
        do {
            System.out.print("Enter menu option: ");
            try {
                selection = input.nextInt();
                if ((selection > max && selection != specialEscape) || selection < 0)
                    System.out.println("Invalid Selection. Please select an option from 1 - " + max);
            } catch (InputMismatchException e) {
                selection = -1;
                System.out.println("Invalid Input. Please only enter numbers");
                System.out.println();
            } finally {
                input.nextLine();
            }
        } while ((selection > max && selection != specialEscape) || selection < 0);
        return selection;
    }
}
