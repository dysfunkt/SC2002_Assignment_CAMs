package cams.util.iocontrol;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cams.model.person.Student;

public class StudentCSVHelper extends CSVBaseHelper {
    private String studentCsv = "student.csv";

    private static StudentCSVHelper mInstance;

    private StudentCSVHelper() {
    }

    public static StudentCSVHelper getInstance() {
        if (mInstance == null) mInstance = new StudentCSVHelper();
        return mInstance;
    }

    public ArrayList<Student> readFromCsv() throws IOException {
        if (!FileIOHelper.exists(this.studentCsv)) return new ArrayList<>(); //Empty ArrayList
        BufferedReader csvFile = FileIOHelper.getFileBufferedReader(this.studentCsv);
        List<String[]> csvLines = readAll(csvFile, 1);
        ArrayList<Student> students = new ArrayList<>();
        if (csvLines.size() == 0) return students;
        for (String[] str : csvLines) {
            Student s = new Student(str);
            students.add(s);
        }
        return students;
    }

    public void writeToCsv(ArrayList<Student> students) throws IOException {
        String[] header = {"Name" ,"Email", "Faculty", "UserID", "Password", 
                            "FirstLogin", "JoinedCamps", "CampCommittee", "Points", 
                            "CampIDCommittingFor"};
        BufferedWriter csvFile = FileIOHelper.getFileBufferedWriter(this.studentCsv);
        ArrayList<String[]> toWrite = new ArrayList<>(); 
        toWrite.add(header);
        students.forEach((s) -> toWrite.add(s.toSaveString()));
        writeToCsvFile(toWrite, csvFile);
    }

    
}
