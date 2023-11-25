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

/**
 * This class handles the issueing of new IDs.
 * Will be saved to data files to ensure coherency.
 */
public class UniqueIDHandler implements Savable{

    /**
     * The camp ID last issued.
     */
    private int campID;

    /**
     * The enquiry ID last issued.
     */
    private int enquiryID;

    /**
     * The suggestion ID last issued.
     */
    private int suggestionID;

    /**
     * The file name of the UniqueID data file.
     */
    private static final String FILE_NAME = "uniqueid.csv";

    /** 
     * Holds the instance of the UniqueIDHandler object
     */
    private static UniqueIDHandler mInstance;

    /**
     * Default constructor of UniqueIDHandler class.
     */
    private UniqueIDHandler(){
        this.campID = 0;
        this.enquiryID = 0;
        this.suggestionID = 0;
    }

    /** 
     * Returns instance of UniqueIDHandler class if it exist; creates one if it does not exist.
     * @return UniqueIDHandler
     */
    public static UniqueIDHandler getInstance() {
        if (mInstance == null) mInstance = new UniqueIDHandler();
        return mInstance;
    }

    /** 
     * Sets the uniqueID.
     * @param csv[]
     */
    private void set(String csv[]) {
        this.campID = Integer.valueOf(csv[0]);
        this.enquiryID = Integer.valueOf(csv[1]);
        this.suggestionID = Integer.valueOf(csv[2]);
    }

    /** 
     * Creates a string with all the savable attributes of the class.
     * @return String[]
     */
    public String[] toSaveString() {
        String[] u = new String[3];
        u[0] = this.campID + "";
        u[1] = this.enquiryID + "";
        u[2] = this.suggestionID + "";
        return u;
    }

    /** 
     * Get the next camp ID.
     * Will increment the camp ID counter.
     * @return The next camp ID.
     */
    public String getNextCampID() {
        this.campID++;
        return campID + "";
    }

    /** 
     * Get the next enquiry ID.
     * Will increment the enquiry ID counter.
     * @return The next enquiry ID.
     */
    public String getNextEnquiryID() {
        this.enquiryID++;
        return enquiryID + "";
    }

    /** 
     * Get the next suggestion ID.
     * Will increment the suggestion ID counter.
     * @return The next suggestion ID.
     */
    public String getNextSuggestionID() {
        this.suggestionID++;
        return suggestionID + "";
    }

    /**
     * Reads CSV file to a String[] list
     *
     * @param reader The BufferedReader object instance to the CSV File.
     * @param skip How many lines in the file to skip (set 1 for header)
     * @return A list of string arrays containing all the CSV values
     */
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

    /**
     * Writes a list of String Arrays to a CSV file
     * Note: This will overwrite the file
     *
     * @param writeStrings List of String arrays to write to
     * @param writer The BufferedWriter object instance to the CSV file.
     */
    private void writeToCsvFile(List<String[]> writeStrings, BufferedWriter writer) {
        PrintWriter w = new PrintWriter(writer);
        writeStrings.forEach((s) -> w.println(String.join("|||", s)));
        w.flush();
        w.close();
    }

    /** 
     * Load unique ID data from save file.
     */
    public void load() throws IOException {
        if (!FileIOHelper.exists(FILE_NAME)) return;
        BufferedReader csvFile = FileIOHelper.getFileBufferedReader(FILE_NAME);
        List<String[]> csvLines = readAll((csvFile), 1);
        if (csvLines.size()==0) return;
        set(csvLines.get(0));
    }

    /** 
     * Save unique ID data to save file.
     */
    public void save() throws IOException {
        String[] header = {"CampID, EnquiryID, SuggestionID"};
        BufferedWriter csvFile = FileIOHelper.getFileBufferedWriter(FILE_NAME);
        ArrayList<String[]> toWrite = new ArrayList<>();
        toWrite.add(header);
        toWrite.add(toSaveString());
        writeToCsvFile(toWrite, csvFile);
    }
}
