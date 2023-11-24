package cams.repository.person;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cams.model.person.Staff;
import cams.repository.Repository;
import cams.util.exception.ModelAlreadyExistsException;
import cams.util.iocontrol.FileIOHelper;

public class StaffRepository extends Repository<Staff> {
    
    private static final String FILE_NAME = "staff.csv";

    private static StaffRepository mInstance;

    private StaffRepository() {
    }

    public static StaffRepository getInstance() {
        if (mInstance == null) mInstance = new StaffRepository();
        return mInstance;
    }

    public void load() throws IOException {
        if (!FileIOHelper.exists(FILE_NAME)) return; //Empty ArrayList
        BufferedReader csvFile = FileIOHelper.getFileBufferedReader(FILE_NAME);
        List<String[]> csvLines = readAll(csvFile, 1);
        if (csvLines.size() == 0) return;
        for (String[] str : csvLines) {
            Staff s = new Staff(str);
            try {
                add(s);
            } catch (ModelAlreadyExistsException e) {
                System.out.println(e.getLocalizedMessage());
            }  
        }
    }

    public void save() throws IOException {
        String[] header = {"Name" ,"Email", "Faculty", "UserID", "Password", "FirstLogin", "CampInChargeID", "CSVPad"};
        BufferedWriter csvFile = FileIOHelper.getFileBufferedWriter(FILE_NAME);
        ArrayList<String[]> toWrite = new ArrayList<>(); 
        toWrite.add(header);
        getList().forEach((s) -> toWrite.add(s.toSaveString()));
        writeToCsvFile(toWrite, csvFile);
    }
}
