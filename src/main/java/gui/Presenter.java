package gui;

import manager.ProducerConsumer;
import model.CityWeather;
import model.Error;

import javax.swing.*;
import java.util.ArrayList;

/**
 * @author Denis
 * @version 1.0
 * Class for managing the graphical interface
 */

public class Presenter implements Runnable {
    MainWindow window;
    CityWeather cityWeather;
    ProducerConsumer<ArrayList<Object>> producerConsumer;

    /**
     * Constructor
     *
     * @param cityWeather      - class-model for data storage
     * @param producerConsumer - class for the implementation of the pattern Producer-Consumer (to exchange data between threads)
     */
    public Presenter(final CityWeather cityWeather, final ProducerConsumer<ArrayList<Object>> producerConsumer) {
        this.cityWeather = cityWeather;
        this.producerConsumer = producerConsumer;
    }

    /**
     * Running a side thread to wait and update data in the GUI
     */
    public void run() {
        try {
            ArrayList<Object> buffer = producerConsumer.consume();
            try {
                String bufferCity = (String) buffer.get(0);
                if (!bufferCity.equals(cityWeather.getCity())) {
                    cityWeather.setCity(bufferCity);
                    cityWeather.setWriteToDisk(true);
                }
                this.showCity(cityWeather.getCity());
                cityWeather.setTempC((Double) buffer.get(1));
                this.showTemp("" + cityWeather.getTempC());
                cityWeather.setLastUpdate(System.nanoTime());
            } catch (Exception e) {
                Error error = (Error) buffer.get(0);
                JFrame f = new JFrame();
                JOptionPane.showMessageDialog(f, error.message);
                if (error.code == 0) {
                    this.showTemp("--");
                }
                if (cityWeather.isFilePreferencesEmpty()) {
                    cityWeather.setNeedUpdate(true);
                    this.showCity(cityWeather.getCity());
                    cityWeather.setFilePreferencesEmpty(false);
                }
            } finally {
                cityWeather.setNewCity("");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method of providing a window for control
     *
     * @param window - a window for control
     */
    public void injectionWindow(final MainWindow window) {
        this.window = window;
    }

    /**
     * Method for launching the city input dialog window when the program is first started
     */
    public void enterCity() {
        JFrame f = new JFrame();
        String city;
        city = JOptionPane.showInputDialog(f, "Enter city:");
        if (city == null || city.equals("")) {
            city = JOptionPane.showInputDialog(f, "If your city is not Moscow. Please, enter city:");
        }
        if (city != null) {
            if (!city.equals("")) {
                cityWeather.setNewCity(city);
            }
        }
    }

    /**
     * Method for displaying a time stamp in the main window
     *
     * @param lastTimeUpdate - a time stamp
     */
    public void setLastTimeUpdate(Long lastTimeUpdate) {
        window.setTimeStamp("Update " + lastTimeUpdate + " sec ago");
    }

    /**
     * The method for displaying a time stamp in the main window
     *
     * @param city - a city
     */
    public void setCityFromMainWindow(String city) {
        if (!city.isEmpty()) {
            cityWeather.setNewCity(city);
            cityWeather.setNeedUpdate(true);
        }
    }

    /**
     * The method for displaying a temperature in the main window
     *
     * @param temp - a temperature to display
     */
    public void showTemp(String temp) {
        window.showTemp(temp);
    }

    /**
     * The method for displaying a city in the main window
     *
     * @param city - a city to display
     */
    public void showCity(String city) {
        window.showCity(city);
    }
}