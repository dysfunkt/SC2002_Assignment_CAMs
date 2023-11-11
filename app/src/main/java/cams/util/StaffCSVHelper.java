package cams.util;

import cams.object.person.Staff;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StaffCSVHelper extends CSVBaseHelper{
    
    private String staffCsv = "staff.csv";

    private static StaffCSVHelper mInstance;

    private StaffCSVHelper() {
    }

    public static StaffCSVHelper getInstance() {
        if (mInstance == null) mInstance = new StaffCSVHelper();
        return mInstance; 
    }

    public ArrayList<Staff> readFromCsv() throws IOException {
        if (!FileIOHelper.exists(this.staffCsv)) return new ArrayList<>(); //Empty ArrayList
        BufferedReader csvFile = FileIOHelper.getFileBufferedReader(this.staffCsv);
        List<String[]> csvLines = readAll(csvFile, 1);
        ArrayList<Staff> staffs = new ArrayList<>();
        if (csvLines.size() == 0) return staffs;
        for (String[] str : csvLines) {
            Staff s = new Staff(str);
            staffs.add(s);
        }
        return staffs;
    }

    public void writeToCsv(ArrayList<Staff> staffs) throws IOException {
        String[] header = {"Name" ,"Email", "Faculty", "UserID", "Password", "FirstLogin"};
        BufferedWriter csvFile = FileIOHelper.getFileBufferedWriter(this.staffCsv);
        ArrayList<String[]> toWrite = new ArrayList<>();
        toWrite.add(header);
        staffs.forEach((s) -> toWrite.add(s.toCsv()));
        writeToCsvFile(toWrite, csvFile);
    }
}
