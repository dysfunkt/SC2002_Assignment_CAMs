package cams.util.iocontrol;

import java.io.*;
/**
 * Helper class for File IO of the CSV files in the data folder.
 * The data folder is where we will be storing the application data.
 */
public class FileIOHelper {
    
    /**
     * Ensure that the data folder exists. Otherwise creates it
     *
     * @return Folder file object
     */
    private static File init() {
        File folder = new File("./data");
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
    public static BufferedReader getFileBufferedReader(String name) throws IOException {
        return new BufferedReader(new FileReader(getFile(name)));
    }
    
    /**
     * Get the Buffered Writer object from the file
     *
     * @param name filename with extension
     * @return Buffered Writer of the file
     * @throws IOException File Not Found Exception
     */
    public static BufferedWriter getFileBufferedWriter(String name) throws IOException {
        return new BufferedWriter(new FileWriter(getFile(name)));
    }
}
