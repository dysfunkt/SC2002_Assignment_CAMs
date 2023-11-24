package cams.repository.appitem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cams.model.appitem.Camp;
import cams.repository.Repository;
import cams.util.exception.ModelAlreadyExistsException;
import cams.util.iocontrol.FileIOHelper;

public class CampRepository extends Repository<Camp>{

    private static final String FILE_NAME = "camp.csv";

    private static CampRepository mInstance;

    private CampRepository() {
    }

    public static CampRepository getInstance() {
        if (mInstance == null) mInstance = new CampRepository();
        return mInstance;
    }

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
