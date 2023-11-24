package cams.util.iocontrol;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cams.model.appitem.Enquiry;

public class EnquiryCSVHelper extends CSVBaseHelper{
    private String enquiryCsv = "enquiry.csv";

    private static EnquiryCSVHelper mInstance;

    private EnquiryCSVHelper() {
    }

    public static EnquiryCSVHelper getInstance() {
        if (mInstance == null) mInstance = new EnquiryCSVHelper();
        return mInstance;
    }

    public ArrayList<Enquiry> readFromCsv() throws IOException {
        if (!FileIOHelper.exists(this.enquiryCsv)) return new ArrayList<>(); //Empty ArrayList
        BufferedReader csvFile = FileIOHelper.getFileBufferedReader(this.enquiryCsv);
        List<String[]> csvLines = readAll(csvFile, 1);
        ArrayList<Enquiry> enquiries = new ArrayList<>();
        if (csvLines.size() == 0) return enquiries;
        for (String[] str : csvLines) {
            Enquiry e = new Enquiry(str);
            enquiries.add(e);
        }
        return enquiries;
    }

    public void writeToCsv(ArrayList<Enquiry> enquiries) throws IOException {
        String[] header = {"EnquiryID", "CampID", "CreatedBy", "Processed", "Deleted", "EnquiryMessage", "EnquiryReply", "ReplyViewed"};
        BufferedWriter csvFile = FileIOHelper.getFileBufferedWriter(this.enquiryCsv);
        ArrayList<String[]> toWrite = new ArrayList<>();
        toWrite.add(header);
        enquiries.forEach((e) -> toWrite.add(e.toCsv()));
        writeToCsvFile(toWrite, csvFile);
    }
}
