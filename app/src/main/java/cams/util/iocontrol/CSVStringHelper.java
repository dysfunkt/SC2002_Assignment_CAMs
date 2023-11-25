package cams.util.iocontrol;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

/*
 * This helper class provides helper methods to format strings in the file I/O process.
 */
public class CSVStringHelper {
    
    /** 
     * Join all elements of ArrayList of strings into one single string.
     * @param list ArrayList to be joined.
     * @return Joined string.
     */
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

    
    /** 
     * Split a string into an ArrayList of elements.
     * @param inputString
     * @return ArrayList of string elements after split.
     */
    public static ArrayList<String> CSVStringtoArraylistString(String inputString) {
        ArrayList<String> resultList = new ArrayList<>();
        if (inputString != null && !inputString.isEmpty()) {
            String[] items = inputString.split(", ");

            // Add the items to the ArrayList
            resultList.addAll(Arrays.asList(items));
        }
        return resultList;
    }

    
    /** 
     * Convert a date in string format into a Date object.
     * @param s String to be converted.
     * @return Date.
     */
    public static Date CSVStringtoDate(String s) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    
    /** 
     * Convert a Date object into a string that can be saved.
     * @param d Date object.
     * @return String.
     */
    public static String DateToCSVString(Date d) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(d);
    }

    
    /** 
     * Split a string into integer elements.
     * @param s String to be split
     * @return ArrayList of integers.
     */
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

    
    /** 
     * Join an ArrayList of integers into a string.
     * @param l Arraylist of integers to be joined.
     * @return Joined string.
     */
    public static String arraylistIntegertoCSVString(ArrayList<Integer> l) {
        StringJoiner stringJoiner = new StringJoiner(",");
        for (Integer item : l) {
            stringJoiner.add(item + "");
        }
        String s = stringJoiner.toString();
        
        return s;
    }
}
