package cams.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/** 
 * Utility class to handle operations with Date objects.
 */
public class DateHandler {
    
    /** 
     * Gets todays date.
     * @return todays date.
     */
    public static Date getTodayDate() {
        java.time.LocalDate today = java.time.LocalDate.now();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(today.toString());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /** 
     * Converts a string to Date object.
     * @param s String to convert.
     * @return Date
     */
    public static Date stringtoDate(String s) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /** 
     * Converts Date to string.
     * @param d Date to convert.
     * @return String
     */
    public static String dateToString(Date d) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(d);
    }
}
