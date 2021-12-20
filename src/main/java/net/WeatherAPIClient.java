package net;

import com.fasterxml.jackson.core.JsonProcessingException;
import manager.ProducerConsumer;
import model.CityWeather;
import model.Error;

import java.util.ArrayList;

/**
 * @author Denis
 * @version 0.3b
 * The class returns the required data for the requested city from the server.
 */
public class WeatherAPIClient implements Runnable {
    CityWeather cityWeather;
    ProducerConsumer<ArrayList<Object>> producerConsumer;

    public WeatherAPIClient(final CityWeather cityWeather, final ProducerConsumer<ArrayList<Object>> producerConsumer) {
        this.cityWeather = cityWeather;
        this.producerConsumer = producerConsumer;
    }

    /**
     * The method gives the temperature for the requested city.
     */
    public void run() {
        String city;
        if (cityWeather.getNewCity().isEmpty()) {
            city = cityWeather.getCity();
        } else {
            city = cityWeather.getNewCity();
        }
        city = city.replace(" ", "_");
        String uri = "http://api.weatherapi.com/v1/current.json?key=df5ff08c050a418eb38122830212003&q=" + city + "&aqi=no";
        HTTPClient httpClient = new HTTPClient();
        String dataFromServer = httpClient.getData(uri);
        JSONParcer jsonParcer = new JSONParcer();
        ArrayList<Object> data = new ArrayList<>();
        if (dataFromServer != null) {
            try {
                data = jsonParcer.readError(dataFromServer);
            } catch (JsonProcessingException e) {
                data = jsonParcer.getCityAndTempFromData(dataFromServer);
            } finally {
                try {
                    producerConsumer.produce(data);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Error error = new Error();
            error.message = "Connection failed!";
            data.add(error);
            try {
                producerConsumer.produce(data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
