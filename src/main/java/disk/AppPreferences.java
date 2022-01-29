package disk;

/**
 * @author Denis
 * @version 1.1
 * Class to save and restore a preference
 */
public class AppPreferences {

    /**
     * Method for saving a preference
     *
     * @param preferences - a city to save to preference
     */
    public void savePreferences(final String preferences) {
        Thread savePrefThready = new Thread(new Runnable() {
            public void run() {
                StorageManager storageManager = new StorageManager();
                storageManager.writeToDisk(preferences);
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
        return storageManager.readFromDisk();
    }
}
