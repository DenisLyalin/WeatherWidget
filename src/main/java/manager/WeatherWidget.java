package manager;

import disk.AppPreferences;
import gui.*;
import model.CityWeather;
import model.BaseResponse;
import net.WeatherAPIClient;

/**
 * @author Denis
 * @version 1.1
 * Main programm's class
 * Create GUI
 * Saves the entered city using AppPreferences
 * Retrieves weather information using WeatherAPIClient
 */

public class WeatherWidget {
    private final CityWeather cityWeather = new CityWeather();
    private final ProducerConsumer<BaseResponse> producerConsumer = new ProducerConsumer<>();
    private final AppPreferences preferences = new AppPreferences();
    private final WeatherAPIClient weatherAPIClient = new WeatherAPIClient(cityWeather, producerConsumer);
    private final Presenter gui = new Presenter(cityWeather, producerConsumer);
    private final MainWindow window = new MainWindow(gui);

    static final long UPDATE_INTERVAL = 60000; //60 seconds
    static final long DISPLAY_DELAY = 5000; //5 seconds

    /**
     * Method for all processes of the program
     */
    public void mainProcess() {
        gui.injectionWindow(window);
        String cityFromDisk = preferences.restoreCity();
        if (cityFromDisk.isEmpty()) {
            preferences.savePreferences(cityWeather.getCity());
            gui.enterCity();
        } else {
            cityWeather.setNewCity(cityFromDisk);
        }
        while (true) {
            if ((System.currentTimeMillis() - cityWeather.getLastUpdate()) > UPDATE_INTERVAL) {
                cityWeather.setNeedUpdate(true);
            }
            if (cityWeather.isNeedUpdate()) {
                cityWeather.setNeedUpdate(false);
                new Thread(weatherAPIClient).start();
                new Thread(gui).start();
                cityWeather.setLastUpdate(System.currentTimeMillis());
            }
            if (cityWeather.isWriteToDisk()) {
                cityWeather.setWriteToDisk(false);
                preferences.savePreferences(cityWeather.getCity());
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if ((System.currentTimeMillis() - cityWeather.getLastUpdate()) >= DISPLAY_DELAY) {
                gui.setTimeStamp((System.currentTimeMillis() - cityWeather.getLastUpdate()) / 1000);
            }
        }
    }
}