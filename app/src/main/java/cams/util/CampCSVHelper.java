package cams.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cams.object.appitem.Camp;


public class CampCSVHelper extends CSVBaseHelper{
    private String campCsv = "camp.csv";

    private static CampCSVHelper mInstance;

    private CampCSVHelper() {
    }

    public static CampCSVHelper getInstance() {
        if (mInstance == null) mInstance = new CampCSVHelper();
        return mInstance;
    }

    public ArrayList<Camp> readFromCsv() throws IOException {
        if (!FileIOHelper.exists(this.campCsv)) return new ArrayList<>(); //empty arraylist
        BufferedReader csvFile = FileIOHelper.getFileBufferedReader(this.campCsv);
        List<String[]> csvLines = readAll(csvFile, 1);
        ArrayList<Camp> camps = new ArrayList<>();
        if (csvLines.size() == 0) return camps;
        for (String[] str : csvLines) {
            Camp c = new Camp(str);
            camps.add(c);
        }
        return camps;
    }

    public void writeToCsv(ArrayList<Camp> camps) throws IOException {
        String[] header = {"CampID", "CampName" ,"StartDate", "EndDate", "RegistrationCloseDate", 
                        "UserGroup", "CampLocation", "CampTotalSlots", "CampCommitteeSlots", 
                        "CampDescription", "staffInCharge", "listOfAttendees", 
                        "listOfCampCommittees", "leavers", "visibility"};
        BufferedWriter csvFile = FileIOHelper.getFileBufferedWriter(this.campCsv);
        ArrayList<String[]> toWrite = new ArrayList<>(); 
        toWrite.add(header);
        camps.forEach((c) -> toWrite.add(c.toCsv()));
        writeToCsvFile(toWrite, csvFile);
    }

    
}
