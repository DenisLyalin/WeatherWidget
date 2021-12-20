package gui;

import manager.ProducerConsumer;
import model.CityWeather;
import model.Error;

import javax.swing.*;
import java.util.ArrayList;

/**
 * @author Denis
 * @version 0.3b
 * Class for creating a graphical interface
 */

public class Presenter implements Runnable {
    MainWindow window;
    CityWeather cityWeather;
    ProducerConsumer<ArrayList<Object>> producerConsumer;

    public Presenter(final CityWeather cityWeather, final ProducerConsumer<ArrayList<Object>> producerConsumer) {
        this.cityWeather = cityWeather;
        this.producerConsumer = producerConsumer;
    }

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
                    cityWeather.setNeedUpdate(true); //если файл пуст и введен не корректный город - перезапускаем обновление
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
     * Method for help window
     * Creating the dialog window
     */
    public void injectionWindow(final MainWindow window) {
        this.window = window;
    }

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

    public void setLastTimeUpdate(Long lastTimeUpdate) {
        window.setTimeStamp("Update " + lastTimeUpdate + " sec ago");
    }

    public void setCityFromMainWindow(String city) {
        if (!city.isEmpty()) {
            cityWeather.setNewCity(city);
            cityWeather.setNeedUpdate(true);
        }
    }

    public void showTemp(String temp) {
        window.showTemp(temp);
    }

    public void showCity(String city) {
        window.showCity(city);
    }
}