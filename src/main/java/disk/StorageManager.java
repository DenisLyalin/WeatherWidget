package disk;

import javax.swing.*;
import java.io.*;

import static javax.swing.JOptionPane.WARNING_MESSAGE;

/**
 * @author Denis
 * @version 1.0
 * Class for saving settings to the file on the hard disk and restoring/from the file from the hard disk
 */

public class StorageManager {
    private final String filePref = "preferences.cfg";

    /**
     * Method for writing data to file
     *
     * @param dataToDisk - data to be written to hard disk
     */
    public void writeToDisk(final String dataToDisk) {
        try (FileWriter writer = new FileWriter(filePref, false)) {
            writer.write(dataToDisk);
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            JFrame f = new JFrame();
            JOptionPane.showMessageDialog(f, "Disc write failed!", "Warning!", WARNING_MESSAGE);
        }
    }

    /**
     * Method for reading data from file
     *
     * @return returns data from file
     */
    public String readFromDisk() {
        String data = null;
        File file = new File(filePref);
        try (FileReader reader = new FileReader(file)) {
            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            data = new String(chars);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return data;
    }
}