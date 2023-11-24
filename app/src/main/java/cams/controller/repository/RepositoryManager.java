package cams.controller.repository;

import java.io.IOException;

import cams.repository.appitem.CampRepository;
import cams.repository.appitem.EnquiryRepository;
import cams.repository.appitem.SuggestionRepository;
import cams.repository.person.StaffRepository;
import cams.repository.person.StudentRepository;

public class RepositoryManager {
    public static void loadAll() {
        try{
            System.out.println("Loading Student infomation from file...");
            StudentRepository.getInstance().load();
            System.out.println(StudentRepository.getInstance().size() + " students loaded successfully");

            System.out.println("Loading Staff infomation from file...");
            StaffRepository.getInstance().load();
            System.out.println(StaffRepository.getInstance().size() + " staffs loaded successfully");

            System.out.println("Loading Camp infomation from file...");
            CampRepository.getInstance().load();
            System.out.println(CampRepository.getInstance().size() + " camps loaded successfully");

            System.out.println("Loading Enquiry infomation from file...");
            EnquiryRepository.getInstance().load();
            System.out.println(EnquiryRepository.getInstance().size() + " enquiries loaded successfully");

            System.out.println("Loading Suggestion infomation from file...");
            SuggestionRepository.getInstance().load();
            System.out.println(SuggestionRepository.getInstance().size() + " suggestions loaded successfully");

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

            System.out.println("Saving current Camp infomation to file...");
            CampRepository.getInstance().save();
            System.out.println("Camp List Saved!");

            System.out.println("Saving current Enquiry infomation to file...");
            EnquiryRepository.getInstance().save();
            System.out.println("Enquiry List Saved!");

            System.out.println("Saving current Suggestion infomation to file...");
            SuggestionRepository.getInstance().save();
            System.out.println("Suggestion List Saved!");

        } catch (IOException e) {
            System.out.println("[ERROR] Failed to save items to file. (" + e.getLocalizedMessage() + ")");
            return false;
        }
        return true;
    }
}
