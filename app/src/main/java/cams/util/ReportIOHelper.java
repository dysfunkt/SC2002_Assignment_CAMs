package cams.util;

import java.io.*;
import java.util.List;

import cams.util.iocontrol.FileIOHelper;

public class ReportIOHelper extends FileIOHelper{
    private static File init() {
        File folder = new File("./data/reports");
        if (!folder.exists()) folder.mkdir();
        return folder;
    }

    public static boolean exists(String name) {
        File folder = init();
        File f = new File(folder.getAbsolutePath() + File.separator + name);
        return f.exists();
    }

    public static boolean createFolder(String name) {
        File folder = init();
        File f = new File(folder.getAbsolutePath() + File.separator + name);
        if (!f.exists()) return f.mkdirs();
        return f.exists();
    }

    public static File getFile(String name) {
        File folder = init();
        return new File(folder.getAbsolutePath() + File.separator + name);
    }

    public static BufferedWriter getFileBufferedWriter(String name) throws IOException {
        return new BufferedWriter(new FileWriter(getFile(name)));
    }

    public static void writeToTxtFile(List<String> writeStrings, BufferedWriter writer) {
        PrintWriter w = new PrintWriter(writer);
        writeStrings.forEach((s) -> w.println(s));
        w.flush();
        w.close();
    }
}
