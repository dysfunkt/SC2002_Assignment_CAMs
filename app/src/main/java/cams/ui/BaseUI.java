package cams.ui;

import cams.MainApp;
import cams.util.ScannerHelper;

import java.util.stream.Stream;
import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class BaseUI {
    private static final int PRINT_MAX_WINDOW_SIZE = 40;

    protected abstract int generateMenuScreen();

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

    protected static void printHeader(String headerName) {
        printHeader(headerName, PRINT_MAX_WINDOW_SIZE);
    }

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

    protected static void printBreaks(int length) {
        Stream.generate(() -> "-").limit(length).forEach((System.out::print));
        System.out.println();
    }

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
                input.nextLine();
            }
            System.out.println();
        } while ((selection > max && selection != specialEscape) || selection < 0);
        return selection;
    }
}