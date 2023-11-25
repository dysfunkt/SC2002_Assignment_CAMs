package cams.controller.report;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

import cams.model.appitem.Enquiry;
import cams.model.camp.Camp;
import cams.model.person.Student;
import cams.repository.appitem.CampRepository;
import cams.repository.appitem.EnquiryRepository;
import cams.repository.person.StudentRepository;
import cams.util.exception.ModelNotFoundException;
import cams.util.iocontrol.ReportIOHelper;


public class ReportFactory {
    
    /** 
     * @param camp
     * @return ArrayList<String>
     */
    private static ArrayList<String> getCampDetails(Camp camp) {
        String[] c = camp.toSaveString();
        ArrayList<String> toWrite = new ArrayList<>();
        toWrite.add("Camp ID: " + c[0]);
        toWrite.add("Camp Name: " + c[1]);
        toWrite.add("Staff-in-charge: " + c[10]);
        toWrite.add("Start Date: " + c[2]);
        toWrite.add("End Date: " + c[3]);
        toWrite.add("Registration Close Date: " + c[4]);
        toWrite.add("Faculty: " + c[5]);
        toWrite.add("Location: " + c[6]);
        toWrite.add("Total Slots: " + c[7]);
        toWrite.add("Camp Committee Slots: " + c[8]);
        toWrite.add("Visibility: " + c[14]);
        toWrite.add("Description: " + c[9]);
        return toWrite;
    }

    public static void generateParticipantListReport(String ID) throws IOException {
        Camp camp;
        try{
            camp = CampRepository.getInstance().getByID(ID);
        } catch (ModelNotFoundException e) {
            System.out.println(e.getLocalizedMessage());
            System.out.println("Report failed to generate.");
            return;
        }
        String fileName = "Camp" + ID + "ParticipantListReport.txt";
        BufferedWriter txtFile = ReportIOHelper.getFileBufferedWriter(fileName);
        ArrayList<String> toWrite = new ArrayList<>();
        toWrite.addAll(getCampDetails(camp));
        toWrite.add("================================================");
        toWrite.add("List of Participants: ");
        String format = "%-10s %-12s %s";
        toWrite.add(String.format(format, "UserID", "Name", "Role"));
        for (String str : camp.getListOfAttendees()) {
            try {
                Student s = StudentRepository.getInstance().getByID(str);
                String formatted = String.format(format, s.getID(), s.getName(), "Attendee");
                toWrite.add(formatted);
            } catch (ModelNotFoundException e) {
                System.out.println(e.getLocalizedMessage());
            }
        }
        for (String str : camp.getListOfCampCommittees()) {
            try {
                Student s = StudentRepository.getInstance().getByID(str);
                String formatted = String.format(format, s.getID(), s.getName(), "Camp Committee");
                toWrite.add(formatted);
            } catch (ModelNotFoundException e) {
                System.out.println(e.getLocalizedMessage());
            }
        }
        ReportIOHelper.writeToTxtFile(toWrite, txtFile);
    }

    public static void generateAttendeeListReport(String ID) throws IOException {
        Camp camp;
        try{
            camp = CampRepository.getInstance().getByID(ID);
        } catch (ModelNotFoundException e) {
            System.out.println(e.getLocalizedMessage());
            System.out.println("Report failed to generate.");
            return;
        }
        String fileName = "Camp" + ID + "AttendeeListReport.txt";
        BufferedWriter txtFile = ReportIOHelper.getFileBufferedWriter(fileName);
        ArrayList<String> toWrite = new ArrayList<>();
        toWrite.addAll(getCampDetails(camp));
        toWrite.add("================================================");
        toWrite.add("List of Attendees: ");
        String format = "%-10s %-12s %s";
        toWrite.add(String.format(format, "UserID", "Name", "Role"));
        for (String str : camp.getListOfAttendees()) {
            try {
                Student s = StudentRepository.getInstance().getByID(str);
                String formatted = String.format(format, s.getID(), s.getName(), "Attendee");
                toWrite.add(formatted);
            } catch (ModelNotFoundException e) {
                System.out.println(e.getLocalizedMessage());
            }
        }
        ReportIOHelper.writeToTxtFile(toWrite, txtFile);
        System.out.println("Report " + fileName + " Generated Successfully.");
    }

    public static void generateCampCommitteeListReport(String ID) throws IOException {
        Camp camp;
        try{
            camp = CampRepository.getInstance().getByID(ID);
        } catch (ModelNotFoundException e) {
            System.out.println(e.getLocalizedMessage());
            System.out.println("Report failed to generate.");
            return;
        }
        String fileName = "Camp" + ID + "CampCommitteeListReport.txt";
        BufferedWriter txtFile = ReportIOHelper.getFileBufferedWriter(fileName);
        ArrayList<String> toWrite = new ArrayList<>();
        toWrite.addAll(getCampDetails(camp));
        toWrite.add("================================================");
        toWrite.add("List of Camp Committees: ");
        String format = "%-10s %-12s %s";
        toWrite.add(String.format(format, "UserID", "Name", "Role"));
        for (String str : camp.getListOfCampCommittees()) {
            try {
                Student s = StudentRepository.getInstance().getByID(str);
                String formatted = String.format(format, s.getID(), s.getName(), "Camp Committee");
                toWrite.add(formatted);
            } catch (ModelNotFoundException e) {
                System.out.println(e.getLocalizedMessage());
            }
        }
        ReportIOHelper.writeToTxtFile(toWrite, txtFile);
        System.out.println("Report " + fileName + " Generated Successfully.");
    }

    public static void generatePerformanceReport(String ID) throws IOException {
        Camp camp;
        try{
            camp = CampRepository.getInstance().getByID(ID);
        } catch (ModelNotFoundException e) {
            System.out.println(e.getLocalizedMessage());
            System.out.println("Report failed to generate.");
            return;
        }
        String fileName = "Camp" + ID + "PerformanceReport.txt";
        BufferedWriter txtFile = ReportIOHelper.getFileBufferedWriter(fileName);
        ArrayList<String> toWrite = new ArrayList<>();
        toWrite.addAll(getCampDetails(camp));
        toWrite.add("================================================");
        toWrite.add("List of Camp Committees: ");
        String format = "%-10s %-12s %s";
        toWrite.add(String.format(format, "UserID", "Name", "Points"));
        for (String str : camp.getListOfCampCommittees()) {
            try {
                Student s = StudentRepository.getInstance().getByID(str);
                String formatted = String.format(format, s.getID(), s.getName(), s.getPoints());
                toWrite.add(formatted);
            } catch (ModelNotFoundException e) {
                System.out.println(e.getLocalizedMessage());
            }
        }
        ReportIOHelper.writeToTxtFile(toWrite, txtFile);
        System.out.println("Report " + fileName + " Generated Successfully.");
    }

    public static void generateStudentEnquiryReport(String ID) throws IOException {
        Camp camp;
        try{
            camp = CampRepository.getInstance().getByID(ID);
        } catch (ModelNotFoundException e) {
            System.out.println(e.getLocalizedMessage());
            System.out.println("Report failed to generate.");
            return;
        }
        String fileName = "Camp" + camp.getID() + "StudentEnquiryReport.txt";
        BufferedWriter txtFile = ReportIOHelper.getFileBufferedWriter(fileName);
        ArrayList<String> toWrite = new ArrayList<>();
        toWrite.addAll(getCampDetails(camp));
        toWrite.add("================================================");
        toWrite.add("Student Enquiries: ");
        toWrite.add("");
        for (Enquiry enquiry: EnquiryRepository.getInstance()) {
            if(enquiry.getCampID() == camp.getID() && !enquiry.isDeleted()) {
                toWrite.add("Enquiry ID: " + enquiry.getID());
                toWrite.add("Created By: " + enquiry.getCreatedBy());
                toWrite.add("Enquiry Message: " + enquiry.getEnquiryMessage());
                toWrite.add("Processed: " + enquiry.isProcessed());
                toWrite.add("Enquiry Reply: " + enquiry.viewReply());
                toWrite.add("");
            }
        }
        ReportIOHelper.writeToTxtFile(toWrite, txtFile);
        System.out.println("Report " + fileName + " Generated Successfully.");
    }
}
