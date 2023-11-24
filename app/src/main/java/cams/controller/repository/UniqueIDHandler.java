package cams.controller.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


import cams.util.iocontrol.FileIOHelper;
import cams.util.iocontrol.Savable;

public class UniqueIDHandler implements Savable{
    private int campID;
    private int enquiryID;
    private int suggestionID;

    private static final String FILE_NAME = "uniqueid.csv";

    private static UniqueIDHandler mInstance;

    private UniqueIDHandler(){
        this.campID = 0;
        this.enquiryID = 0;
        this.suggestionID = 0;
    }

    public static UniqueIDHandler getInstance() {
        if (mInstance == null) mInstance = new UniqueIDHandler();
        return mInstance;
    }

    private void set(String csv[]) {
        this.campID = Integer.valueOf(csv[0]);
        this.enquiryID = Integer.valueOf(csv[1]);
        this.suggestionID = Integer.valueOf(csv[2]);
    }

    public String[] toSaveString() {
        String[] u = new String[3];
        u[0] = this.campID + "";
        u[1] = this.enquiryID + "";
        u[2] = this.suggestionID + "";
        return u;
    }

    public String getNextCampID() {
        this.campID++;
        return campID + "";
    }

    public String getNextEnquiryID() {
        this.enquiryID++;
        return enquiryID + "";
    }


    public String getNextSuggestionID() {
        this.suggestionID++;
        return suggestionID + "";
    }

    private List<String[]> readAll(BufferedReader reader, int skip) {
        List<String> tmp = reader.lines().collect(Collectors.toList());
        if (tmp.size() == 0) return new ArrayList<>(); // Empty CSV file
        IntStream.range(0, skip).forEach((i) -> tmp.remove(0));
        List<String[]> result = new ArrayList<>();
        tmp.forEach((s) -> {
            if (s.trim().isEmpty()) return; // Ignore this line
            result.add(s.split("\\|\\|\\|"));
        });
        return result;
    }

    private void writeToCsvFile(List<String[]> writeStrings, BufferedWriter writer) {
        PrintWriter w = new PrintWriter(writer);
        writeStrings.forEach((s) -> w.println(String.join("|||", s)));
        w.flush();
        w.close();
    }

    public void load() throws IOException {
        if (!FileIOHelper.exists(FILE_NAME)) return;
        BufferedReader csvFile = FileIOHelper.getFileBufferedReader(FILE_NAME);
        List<String[]> csvLines = readAll((csvFile), 1);
        if (csvLines.size()==0) return;
        set(csvLines.get(0));
    }

    public void save() throws IOException {
        String[] header = {"CampID, EnquiryID, SuggestionID"};
        BufferedWriter csvFile = FileIOHelper.getFileBufferedWriter(FILE_NAME);
        ArrayList<String[]> toWrite = new ArrayList<>();
        toWrite.add(header);
        toWrite.add(toSaveString());
        writeToCsvFile(toWrite, csvFile);
    }
}
