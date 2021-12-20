package manager;

import disk.AppPreferences;
import gui.MainWindow;
import gui.Presenter;
import model.CityWeather;
import net.WeatherAPIClient;

import java.util.ArrayList;

/**
 * @author Denis
 * @version 0.3b
 * Main programm's class
 * Create GUI
 * Saves the entered city using AppPreferences
 * Retrieves weather information using WeatherAPIClient
 */

public class WeatherWidget {
    private final CityWeather cityWeather = new CityWeather();
    private final ProducerConsumer<ArrayList<Object>> producerConsumer = new ProducerConsumer<>();
    private final AppPreferences preferences = new AppPreferences();
    private final WeatherAPIClient weatherAPIClient = new WeatherAPIClient(cityWeather, producerConsumer);
    private final Presenter gui = new Presenter(cityWeather, producerConsumer);
    private final MainWindow window = new MainWindow(gui);

    /**
     * Method for all processes of the program
     */
    public void mainProcess() {
        gui.injectionWindow(window);
        String cityFromDisk = preferences.restoreCity();
        if (cityFromDisk.isEmpty()) {
            cityWeather.setFilePreferencesEmpty(true);
            preferences.saverCityThread(cityWeather.getCity());
            gui.enterCity();
        } else {
            gui.showCity(cityFromDisk);
            cityWeather.setNewCity(cityFromDisk);
            cityWeather.setCity(cityFromDisk);
        }
        cityWeather.setLastUpdate(System.nanoTime());
        while (true) {
            if ((System.nanoTime() - cityWeather.getLastUpdate()) > 60000000000L) {
                cityWeather.setLastUpdate(System.nanoTime());
                cityWeather.setNeedUpdate(true);
            }
            if (cityWeather.isNeedUpdate()) {
                cityWeather.setNeedUpdate(false);
                new Thread(weatherAPIClient).start();
                new Thread(gui).start();
            }
            if (cityWeather.isWriteToDisk()) {
                cityWeather.setWriteToDisk(false);
                preferences.saverCityThread(cityWeather.getCity());
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (((System.nanoTime() - cityWeather.getLastUpdate()) / 1000000000L) >= 10) {
                gui.setLastTimeUpdate((System.nanoTime() - cityWeather.getLastUpdate()) / 1000000000L);
            }
        }
    }
}
