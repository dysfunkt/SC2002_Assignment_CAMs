package cams.repository.appitem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cams.model.appitem.Suggestion;
import cams.repository.Repository;
import cams.util.exception.ModelAlreadyExistsException;
import cams.util.iocontrol.FileIOHelper;

/** 
 * This class is a repository that manages the persistence of Suggestion objects across different runtime instances through file I/O operations.
 * It extends the Repository class, which provides the basic CRUD operations for the repository.
 */
public class SuggestionRepository extends Repository<Suggestion>{
    
    /** 
     * The file name of the Suggestion data file
     */
    private static final String FILE_NAME = "suggestion.csv";

    /** 
     * Holds the instance of the SuggestionRepository object
     */
    private static SuggestionRepository mInstance;

    /** 
     * Default constructor of SuggestionRepository class
     */
    private SuggestionRepository() {
    }

    /** 
     * Returns object instance, creates a new object not yet created.
     * @return the SuggestionRepository object instance
     */
    public static SuggestionRepository getInstance() {
        if (mInstance == null) mInstance = new SuggestionRepository();
        return mInstance;
    }

    /** 
     * Load list of Suggestion from repository file.
     */
    public void load() throws IOException {
        if (!FileIOHelper.exists(FILE_NAME)) return; //Empty ArrayList
        BufferedReader csvFile = FileIOHelper.getFileBufferedReader(FILE_NAME);
        List<String[]> csvLines = readAll(csvFile, 1);
        if (csvLines.size() == 0) return;
        for (String[] str : csvLines) {
            Suggestion s = new Suggestion(str);
            try {
                add(s);
            } catch (ModelAlreadyExistsException e) {
                System.out.println(e.getLocalizedMessage());
            }  
        }
    }

    /** 
     * Save list of Suggestion from repository file.
     */
    public void save() throws IOException {
        String[] header = {"SuggestionID", "CampID", "CreatedBy", "Processed", "Deleted", "SuggestionMessage",  "Approved"};
        BufferedWriter csvFile = FileIOHelper.getFileBufferedWriter(FILE_NAME);
        ArrayList<String[]> toWrite = new ArrayList<>(); 
        toWrite.add(header);
        getList().forEach((s) -> toWrite.add(s.toSaveString()));
        writeToCsvFile(toWrite, csvFile);
    }
}
