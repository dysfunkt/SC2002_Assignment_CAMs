package cams.repository.person;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cams.model.person.Student;
import cams.repository.Repository;
import cams.util.exception.ModelAlreadyExistsException;
import cams.util.iocontrol.FileIOHelper;


public class StudentRepository extends Repository<Student> {
    private static final String FILE_NAME = "student.csv";

    private static StudentRepository mInstance;

    private StudentRepository() {
    }

    public static StudentRepository getInstance() {
        if (mInstance == null) mInstance = new StudentRepository();
        return mInstance;
    }

    public void load() throws IOException {
        if (!FileIOHelper.exists(FILE_NAME)) return; //Empty ArrayList
        BufferedReader csvFile = FileIOHelper.getFileBufferedReader(FILE_NAME);
        List<String[]> csvLines = readAll(csvFile, 1);
        if (csvLines.size() == 0) return;
        for (String[] str : csvLines) {
            Student s = new Student(str);
            try {
                add(s);
            } catch (ModelAlreadyExistsException e) {
                System.out.println(e.getLocalizedMessage());
            }  
        }
    }

    public void save() throws IOException {
        String[] header = {"Name" ,"Email", "Faculty", "UserID", "Password", 
                            "FirstLogin", "JoinedCamps", "CampCommittee", "Points", 
                            "CampIDCommittingFor"};
        BufferedWriter csvFile = FileIOHelper.getFileBufferedWriter(FILE_NAME);
        ArrayList<String[]> toWrite = new ArrayList<>(); 
        toWrite.add(header);
        getList().forEach((s) -> toWrite.add(s.toSaveString()));
        writeToCsvFile(toWrite, csvFile);
    }
}
