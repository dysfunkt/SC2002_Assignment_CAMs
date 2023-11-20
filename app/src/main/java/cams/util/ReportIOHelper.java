package cams.util;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

import cams.MainApp;
import cams.object.appitem.Camp;
import cams.object.appitem.Enquiry;

public class ReportIOHelper extends FileIOHelper{
    private static File init() {
        File folder = new File("./data/reports");
        if (!folder.exists()) folder.mkdir();
        return folder;
    }

    public static boolean exists(String name) {
        File folder = init();
        File f = new File(folder.getAbsolutePath() + File.separator + name);
        return f.exists();
    }

    public static boolean createFolder(String name) {
        File folder = init();
        File f = new File(folder.getAbsolutePath() + File.separator + name);
        if (!f.exists()) return f.mkdirs();
        return f.exists();
    }

    public static File getFile(String name) {
        File folder = init();
        return new File(folder.getAbsolutePath() + File.separator + name);
    }

    public static BufferedReader getFileBufferedReader(String name) throws IOException {
        return new BufferedReader(new FileReader(getFile(name)));
    }

    public static BufferedWriter getFileBufferedWriter(String name) throws IOException {
        return new BufferedWriter(new FileWriter(getFile(name)));
    }

    private static void writeToTxtFile(List<String> writeStrings, BufferedWriter writer) {
        PrintWriter w = new PrintWriter(writer);
        writeStrings.forEach((s) -> w.println(s));
        w.flush();
        w.close();
    }

    private static ArrayList<String> getCampDetails(Camp camp) {
        String[] c = camp.toCsv();
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

    public static void generateParticipantListReport(Camp camp) throws IOException {
        String fileName = "Camp" + camp.getCampID() + "ParticipantListReport.txt";
        BufferedWriter txtFile = getFileBufferedWriter(fileName);
        ArrayList<String> toWrite = new ArrayList<>();
        toWrite.addAll(getCampDetails(camp));
        toWrite.add("================================================");
        toWrite.add("List of Participants: ");
        String format = "%-10s %-12s %s";
        toWrite.add(String.format(format, "UserID", "Name", "Role"));
        for (String str : camp.getListOfAttendees()) {
            String formatted = String.format(format, str, IDHelper.getStudentFromUserID(str).getName(), "Attendee");
            toWrite.add(formatted);
        }
        for (String str : camp.getListOfCampCommittees()) {
            String formatted = String.format(format, str, IDHelper.getStudentFromUserID(str).getName(), "Camp Committee");
            toWrite.add(formatted);
        }
        writeToTxtFile(toWrite, txtFile);
    }

    public static void generateAttendeeListReport(Camp camp) throws IOException {
        String fileName = "Camp" + camp.getCampID() + "AttendeeListReport.txt";
        BufferedWriter txtFile = getFileBufferedWriter(fileName);
        ArrayList<String> toWrite = new ArrayList<>();
        toWrite.addAll(getCampDetails(camp));
        toWrite.add("================================================");
        toWrite.add("List of Attendees: ");
        String format = "%-10s %-12s %s";
        toWrite.add(String.format(format, "UserID", "Name", "Role"));
        for (String str : camp.getListOfAttendees()) {
            String formatted = String.format(format, str, IDHelper.getStudentFromUserID(str).getName(), "Attendee");
            toWrite.add(formatted);
        }
        writeToTxtFile(toWrite, txtFile);
        System.out.println("Report " + fileName + " Generated Successfully.");
    }

    public static void generateCampCommitteeListReport(Camp camp) throws IOException {
        String fileName = "Camp" + camp.getCampID() + "CampCommitteeListReport.txt";
        BufferedWriter txtFile = getFileBufferedWriter(fileName);
        ArrayList<String> toWrite = new ArrayList<>();
        toWrite.addAll(getCampDetails(camp));
        toWrite.add("================================================");
        toWrite.add("List of Camp Committees: ");
        String format = "%-10s %-12s %s";
        toWrite.add(String.format(format, "UserID", "Name", "Role"));
        for (String str : camp.getListOfCampCommittees()) {
            String formatted = String.format(format, str, IDHelper.getStudentFromUserID(str).getName(), "Camp Committee");
            toWrite.add(formatted);
        }
        writeToTxtFile(toWrite, txtFile);
        System.out.println("Report " + fileName + " Generated Successfully.");
    }

    public static void generatePerformanceReport(Camp camp) throws IOException {
        String fileName = "Camp" + camp.getCampID() + "PerformanceReport.txt";
        BufferedWriter txtFile = getFileBufferedWriter(fileName);
        ArrayList<String> toWrite = new ArrayList<>();
        toWrite.addAll(getCampDetails(camp));
        toWrite.add("================================================");
        toWrite.add("List of Camp Committees: ");
        String format = "%-10s %-12s %s";
        toWrite.add(String.format(format, "UserID", "Name", "Points"));
        for (String str : camp.getListOfCampCommittees()) {
            String formatted = String.format(format, str, IDHelper.getStudentFromUserID(str).getName(), IDHelper.getStudentFromUserID(str).getPoints()+"");
            toWrite.add(formatted);
        }
        writeToTxtFile(toWrite, txtFile);
        System.out.println("Report " + fileName + " Generated Successfully.");
    }

    public static void generateStudentEnquiryReport(Camp camp) throws IOException {
        String fileName = "Camp" + camp.getCampID() + "StudentEnquiryReport.txt";
        BufferedWriter txtFile = getFileBufferedWriter(fileName);
        ArrayList<String> toWrite = new ArrayList<>();
        toWrite.addAll(getCampDetails(camp));
        toWrite.add("================================================");
        toWrite.add("Student Enquiries: ");
        toWrite.add("");
        for (Enquiry enquiry: MainApp.enquiries) {
            if(enquiry.getCampID() == camp.getCampID() && !enquiry.isDeleted()) {
                toWrite.add("Enquiry ID: " + enquiry.getEnquiryID());
                toWrite.add("Created By: " + enquiry.getCreatedBy());
                toWrite.add("Enquiry Message: " + enquiry.getEnquiryMessage());
                toWrite.add("Processed: " + enquiry.isProcessed());
                toWrite.add("Enquiry Reply: " + enquiry.viewReply());
                toWrite.add("");
            }
        }
        writeToTxtFile(toWrite, txtFile);
        System.out.println("Report " + fileName + " Generated Successfully.");
    }
}
