package cams.controller.repository;

import java.io.IOException;

import cams.repository.person.StaffRepository;
import cams.repository.person.StudentRepository;

public class RepositoryManager {
    public static void init() {
        try{
            System.out.println("Loading Student infomation from file...");
            StudentRepository.getInstance().load();
            System.out.println(StudentRepository.getInstance().size() + " students loaded successfully");

            System.out.println("Loading Staff infomation from file...");
            StaffRepository.getInstance().load();
            System.out.println(StaffRepository.getInstance().size() + " staffs loaded successfully");
        } catch (IOException e) {
            System.out.println("[ERROR] Failed to read CSV from data folder. (" + e.getLocalizedMessage() + ")");
        }
    }

    public static boolean saveAll() {
        try{
            System.out.println("Saving current Student infomation to file...");
            StudentRepository.getInstance().save();
            System.out.println("Student List Saved!");

            System.out.println("Saving current Staff infomation to file...");
            StaffRepository.getInstance().save();
            System.out.println("Staff List Saved!");
        } catch (IOException e) {
            System.out.println("[ERROR] Failed to save items to file. (" + e.getLocalizedMessage() + ")");
            return false;
        }
        return true;
    }
    
}
