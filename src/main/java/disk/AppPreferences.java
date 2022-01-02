package disk;

import java.util.Objects;

/**
 * @author Denis
 * @version 1.0
 * Class to save and restore a preference
 */
public class AppPreferences {

    /**
     * Method for saving a preference
     *
     * @param city - a city to save to preference
     */
    public void saverCityThread(final String city) {
        Thread savePrefThready = new Thread(new Runnable() {
            public void run() {
                if (!city.equals("")) {
                    StorageManager storageManager = new StorageManager();
                    storageManager.writeToDisk(city);
                }
            }
        });
        savePrefThready.start();
    }

    /**
     * Method for restoring a preference
     *
     * @return returns the stored a city
     */
    public String restoreCity() {
        StorageManager storageManager = new StorageManager();
        return Objects.requireNonNullElse(storageManager.readFromDisk(), "");
    }
}
