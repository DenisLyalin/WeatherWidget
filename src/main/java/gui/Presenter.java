package gui;

import manager.ProducerConsumer;
import model.*;
import model.Error;

import javax.swing.*;

/**
 * @author Denis
 * @version 1.1
 * Class for managing the graphical interface
 */

public class Presenter implements Runnable {
    private MainWindow window;
    private final CityWeather cityWeather;
    private final ProducerConsumer<BaseResponse> producerConsumer;

    /**
     * Constructor
     *
     * @param cityWeather      - class-model for data storage
     * @param producerConsumer - class for the implementation of the pattern Producer-Consumer (to exchange data between threads)
     */
    public Presenter(final CityWeather cityWeather, final ProducerConsumer<BaseResponse> producerConsumer) {
        this.cityWeather = cityWeather;
        this.producerConsumer = producerConsumer;
    }

    /**
     * Running a side thread to wait and update data in the GUI
     */
    public void run() {
        try {
            BaseResponse baseResponse = producerConsumer.consume();
            if (baseResponse instanceof CurrentWeatherResponse) {
                CurrentWeatherResponse currentWeatherResponse = (CurrentWeatherResponse) baseResponse;
                String bufferCity = currentWeatherResponse.location.name;
                if (!bufferCity.equals(cityWeather.getCity())) {
                    cityWeather.setCity(bufferCity);
                    cityWeather.setWriteToDisk(true);
                }
                this.showCity(cityWeather.getCity());
                cityWeather.setTempC(currentWeatherResponse.current.tempC);
                this.showTemp("" + cityWeather.getTempC());
            } else {
                ErrorResponse errorResponse = (ErrorResponse) baseResponse;
                Error error = errorResponse.error;
                JFrame f = new JFrame();
                JOptionPane.showMessageDialog(f, error.message);
                if (error.code == 0) {
                    this.showTemp("\u2014");
                }
                cityWeather.setNeedUpdate(true);
            }
            cityWeather.setNewCity("");
        } catch (InterruptedException ignore) {
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
        if (city != null) {
            city = city.trim();
            if (!city.isEmpty()) {
                cityWeather.setNewCity(city);
            } else {
                cityWeather.setWriteToDisk(true);
            }
        } else {
            cityWeather.setWriteToDisk(true);
        }
    }

    /**
     * Method for displaying a time stamp in the main window
     *
     * @param lastTimeUpdate - a time stamp
     */
    public void setTimeStamp(Long lastTimeUpdate) {
        window.setTimeStamp("Update " + lastTimeUpdate + " sec ago");
    }

    /**
     * The method for displaying a time stamp in the main window
     *
     * @param city - a city
     */
    public void setCityFromMainWindow(String city) {
        city = city.trim();
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