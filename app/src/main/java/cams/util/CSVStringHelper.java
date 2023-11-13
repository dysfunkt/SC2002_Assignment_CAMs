package cams.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

public class CSVStringHelper {
    public static String arraylistStringtoCSVString(ArrayList<String> l) {
        StringJoiner stringJoiner = new StringJoiner(",");
        for (String item : l) {
            stringJoiner.add(item);
        }
        String s = stringJoiner.toString();
        
        return s;
    }

    public static ArrayList<String> CSVStringtoArraylistString(String s) {
        String[] splitArray = s.split(",");        
        ArrayList<String> l = new ArrayList<>(Arrays.asList(splitArray));

        return l;
    }

    public static Date CSVStringtoDate(String s) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String DateToCSVString(Date d) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(d);
    }

    public static ArrayList<Integer> CSVStringtoArraylistInteger(String s) {
        ArrayList<Integer> intList = new ArrayList<>();
        if (s.isEmpty()) return intList;
        List<String> stringList = Arrays.asList(s.trim().split("\\s*,\\s*"));
        for (String str : stringList) {
            // Parse each string element to an integer and add it to the ArrayList
            intList.add(Integer.parseInt(str));
        }
        return intList;
    }

    public static String arraylistIntegertoCSVString(ArrayList<Integer> l) {
        StringJoiner stringJoiner = new StringJoiner(",");
        for (Integer item : l) {
            stringJoiner.add(item + "");
        }
        String s = stringJoiner.toString();
        
        return s;
    }
}
