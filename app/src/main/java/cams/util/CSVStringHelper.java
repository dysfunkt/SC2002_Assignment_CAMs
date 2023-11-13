package cams.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

public class CSVStringHelper {
    public static String arraylistStringtoCSVString(ArrayList<String> list) {
        if (list.isEmpty()) {
            return ""; 
        }
        if (list.size() == 1) {
            return list.get(0); // Return the single item as is
        }
        StringBuilder result = new StringBuilder();
        result.append(list.get(0));
        for (int i = 1; i < list.size(); i++) {
            result.append(",").append(list.get(i));
        }

        return result.toString();
    }

    public static ArrayList<String> CSVStringtoArraylistString(String inputString) {
        ArrayList<String> resultList = new ArrayList<>();
        if (inputString != null && !inputString.isEmpty()) {
            String[] items = inputString.split(", ");

            // Add the items to the ArrayList
            resultList.addAll(Arrays.asList(items));
        }
        return resultList;
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
