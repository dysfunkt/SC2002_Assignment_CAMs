package cams.util.iocontrol;

import java.io.*;
import java.util.List;
/**
 * Helper class for File IO of the report files in the reports folder.
 * The reports folder is where we will be storing the generated reports.
 */
public class ReportIOHelper {
    
    /**
     * Ensure that the reports folder exists. Otherwise creates it
     *
     * @return Folder file object
     */
    private static File init() {
        File folder = new File("./data/reports");
        if (!folder.exists()) folder.mkdir();
        return folder;
    }

    
    /**
     * Check if file exists
     *
     * @param name File Name
     * @return true if exist
     */
    public static boolean exists(String name) {
        File folder = init();
        File f = new File(folder.getAbsolutePath() + File.separator + name);
        return f.exists();
    }

    
    /**
     * Creates a folder in the data directory
     *
     * @param name File Name
     * @return true if created
     */
    public static boolean createFolder(String name) {
        File folder = init();
        File f = new File(folder.getAbsolutePath() + File.separator + name);
        if (!f.exists()) return f.mkdirs();
        return f.exists();
    }

    
    /**
     * Gets the file object in the data folder
     *
     * @param name Filename with extension
     * @return File object if valid, null otherwise
     */
    public static File getFile(String name) {
        File folder = init();
        return new File(folder.getAbsolutePath() + File.separator + name);
    }

    
    /**
     * Get the buffered reader object from the file
     *
     * @param name Filename with extension
     * @return Buffered Reader of the file
     * @throws IOException File Not Found Exception
     */
    public static BufferedWriter getFileBufferedWriter(String name) throws IOException {
        return new BufferedWriter(new FileWriter(getFile(name)));
    }

    
    /**
     * Get the Buffered Writer object from the file
     *
     * @param name filename with extension
     * @return Buffered Writer of the file
     * @throws IOException File Not Found Exception
     */
    public static void writeToTxtFile(List<String> writeStrings, BufferedWriter writer) {
        PrintWriter w = new PrintWriter(writer);
        writeStrings.forEach((s) -> w.println(s));
        w.flush();
        w.close();
    }
}
