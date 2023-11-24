package cams.util.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import cams.util.date.DateHandler;
import cams.util.iocontrol.FileIOHelper;

public class Welcome {
    public static void printWelcomeAscii() {
        try {
            BufferedReader reader = FileIOHelper.getFileBufferedReader("welcome_ascii.txt");
            List<String> ascii = reader.lines().collect(Collectors.toList());
            ascii.forEach(System.out::println);
        } catch (IOException e) {
            System.out.println("[ERROR] Failed to load ASCII Welcome Art!");
        }
    }

    public static void getGreeting() {
        System.out.println("Welcome to Camp Management System!");
        System.out.println("Today's date: " + DateHandler.getTodayDate());
    }
}
