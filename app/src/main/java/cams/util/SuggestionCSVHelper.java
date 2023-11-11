package cams.util;

import cams.object.appitem.Suggestion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SuggestionCSVHelper extends CSVBaseHelper{
    private String suggestionCsv = "suggestion.csv";

    private static SuggestionCSVHelper mInstance;

    private SuggestionCSVHelper(){
    }

    public static SuggestionCSVHelper getInstance() {
        if (mInstance == null) mInstance = new SuggestionCSVHelper();
        return mInstance;
    }

    public ArrayList<Suggestion> readFromCsv() throws IOException {
        if (!FileIOHelper.exists(this.suggestionCsv)) return new ArrayList<>(); //Empty ArrayList
        BufferedReader csvFile = FileIOHelper.getFileBufferedReader(this.suggestionCsv);
        List<String[]> csvLines = readAll(csvFile, 1);
        ArrayList<Suggestion> suggestions = new ArrayList<>();
        if (csvLines.size() == 0) return suggestions;
        for (String[] str : csvLines) {
            Suggestion s = new Suggestion(str);
            suggestions.add(s);
        }
        return suggestions;
    }

    public void writeToCsv(ArrayList<Suggestion> suggestions) throws IOException {
        String[] header = {"SuggestionID", "CampID", "CreatedBy", "Processed", "Deleted", "SuggestionMessage",  "Approved"};
        BufferedWriter csvFile = FileIOHelper.getFileBufferedWriter(this.suggestionCsv);
        ArrayList<String[]> toWrite = new ArrayList<>();
        toWrite.add(header);
        suggestions.forEach((s) -> toWrite.add(s.toCsv()));
        writeToCsvFile(toWrite, csvFile);
    }
}
