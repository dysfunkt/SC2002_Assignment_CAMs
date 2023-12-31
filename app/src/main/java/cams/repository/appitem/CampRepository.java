package cams.repository.appitem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cams.model.camp.Camp;
import cams.repository.Repository;
import cams.util.exception.ModelAlreadyExistsException;
import cams.util.iocontrol.FileIOHelper;

/** 
 * This class is a repository that manages the persistence of Camp objects across different runtime instances through file I/O operations.
 * It extends the Repository class, which provides the basic CRUD operations for the repository.
 */
public class CampRepository extends Repository<Camp>{

    /** 
     * The file name of the camp data file
     */
    private static final String FILE_NAME = "camp.csv";

    /** 
     * Holds the instance of the CampRepository object
     */
    private static CampRepository mInstance;

    /** 
     * Default constructor of the CampRepository class
     */
    private CampRepository() {
    }

    
    /** 
     * Returns object instance, creates a new object if not yet created.
     * @return the CampRepository object instance
     */
    public static CampRepository getInstance() {
        if (mInstance == null) mInstance = new CampRepository();
        return mInstance;
    }

    /** 
     * Load list of Camp from repository file.
     */
    public void load() throws IOException {
        if (!FileIOHelper.exists(FILE_NAME)) return; //Empty ArrayList
        BufferedReader csvFile = FileIOHelper.getFileBufferedReader(FILE_NAME);
        List<String[]> csvLines = readAll(csvFile, 1);
        if (csvLines.size() == 0) return;
        for (String[] str : csvLines) {
            Camp c = new Camp(str);
            try {
                add(c);
            } catch (ModelAlreadyExistsException e) {
                System.out.println(e.getLocalizedMessage());
            }  
        }
    }

    /** 
     * Save list of Camp from repository file.
     */
    public void save() throws IOException {
        String[] header = {"CampID", "CampName" ,"StartDate", "EndDate", "RegistrationCloseDate", 
                        "UserGroup", "CampLocation", "CampTotalSlots", "CampCommitteeSlots", 
                        "CampDescription", "staffInCharge", "listOfAttendees", 
                        "ListOfCampCommittees", "Leavers", "Visibility"};
        BufferedWriter csvFile = FileIOHelper.getFileBufferedWriter(FILE_NAME);
        ArrayList<String[]> toWrite = new ArrayList<>(); 
        toWrite.add(header);
        getList().forEach((s) -> toWrite.add(s.toSaveString()));
        writeToCsvFile(toWrite, csvFile);
    }
}
