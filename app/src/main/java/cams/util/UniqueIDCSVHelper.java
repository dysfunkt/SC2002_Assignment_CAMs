package cams.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cams.object.appitem.UniqueID;

public class UniqueIDCSVHelper extends CSVBaseHelper{
    private String uniqueIDCsv = "uniqueid.csv";

    private static UniqueIDCSVHelper mInstance;

    private UniqueIDCSVHelper() {
    }

    public static UniqueIDCSVHelper getInstance() {
        if (mInstance == null) mInstance = new UniqueIDCSVHelper();
        return mInstance; 
    }

    public UniqueID readFromCsv() throws IOException {
        if (!FileIOHelper.exists(this.uniqueIDCsv)) return new UniqueID();
        BufferedReader csvFile = FileIOHelper.getFileBufferedReader(this.uniqueIDCsv);
        List<String[]> csvLines = readAll((csvFile), 1);
        UniqueID uniqueID = new UniqueID(csvLines.get(0));
        return uniqueID;
    }

    public void writeToCsv(UniqueID uniqueID) throws IOException {
        String[] header = {"CampID, EnquiryID, SuggestionID"};
        BufferedWriter csvFile = FileIOHelper.getFileBufferedWriter(this.uniqueIDCsv);
        ArrayList<String[]> toWrite = new ArrayList<>();
        toWrite.add(header);
        toWrite.add(uniqueID.toCsv());
        writeToCsvFile(toWrite, csvFile);
    }
}
