package cams.repository.appitem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import cams.model.appitem.Enquiry;
import cams.repository.Repository;
import cams.util.exception.ModelAlreadyExistsException;
import cams.util.iocontrol.FileIOHelper;

public class EnquiryRepository extends Repository<Enquiry>{

    private static final String FILE_NAME = "Enquiry.csv";

    private static EnquiryRepository mInstance;

    private EnquiryRepository() {
    }
    
    public static EnquiryRepository getInstance() {
        if (mInstance == null) mInstance = new EnquiryRepository();
        return mInstance;
    }

    public void load() throws IOException {
        if (!FileIOHelper.exists(FILE_NAME)) return; //Empty ArrayList
        BufferedReader csvFile = FileIOHelper.getFileBufferedReader(FILE_NAME);
        List<String[]> csvLines = readAll(csvFile, 1);
        if (csvLines.size() == 0) return;
        for (String[] str : csvLines) {
            Enquiry en = new Enquiry(str);
            try {
                add(en);
            } catch (ModelAlreadyExistsException e) {
                System.out.println(e.getLocalizedMessage());
            }  
        }
    }

    public void save() throws IOException {
        String[] header = {"EnquiryID", "CampID", "CreatedBy", "Processed", "Deleted", "EnquiryMessage", "EnquiryReply", "ReplyViewed"};
        BufferedWriter csvFile = FileIOHelper.getFileBufferedWriter(FILE_NAME);
        ArrayList<String[]> toWrite = new ArrayList<>(); 
        toWrite.add(header);
        getList().forEach((s) -> toWrite.add(s.toSaveString()));
        writeToCsvFile(toWrite, csvFile);
    }
}
