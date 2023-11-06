package cams.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CSVBaseHelper {

    protected List<String[]> readAll(BufferedReader reader, int skip) {
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

    protected void writeToCsvFile(List<String[]> writeStrings, BufferedWriter writer) {
        PrintWriter w = new PrintWriter(writer);
        writeStrings.forEach((s) -> w.println(String.join("|||", s)));
        w.flush();
        w.close();
    }
}
