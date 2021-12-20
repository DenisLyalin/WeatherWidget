package model;

/**
 * @author Denis
 * @version 0.3b
 * The model class used to change and display the city name and temperature
 * also for exchanging data between threads
 */

public class CityWeather {

    private String city = "Moscow";
    private Double TempC = 0.0;
    private boolean needUpdate = true;
    private boolean writeToDisk = false;
    private boolean filePreferencesEmpty = false;
    private String newCity = "Moscow";
    private long lastUpdate = 0;

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public Double getTempC() {
        return TempC;
    }

    public void setTempC(Double tempC) {
        TempC = tempC;
    }

    public Long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(final Long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public synchronized void setNeedUpdate(final boolean cityChanged) {
        this.needUpdate = cityChanged;
    }

    public boolean isNeedUpdate() {
        return needUpdate;
    }

    public void setNewCity (final String newCity) {
        this.newCity = newCity;
    }

    public String getNewCity () {
        return newCity;
    }

    public synchronized void setWriteToDisk(final boolean writeToDisk) {
        this.writeToDisk = writeToDisk;
    }

    public boolean isWriteToDisk() {
        return writeToDisk;
    }

    public boolean isFilePreferencesEmpty() {
        return filePreferencesEmpty;
    }

    public void setFilePreferencesEmpty(boolean filePreferencesEmpty) {
        this.filePreferencesEmpty = filePreferencesEmpty;
    }
}
