package net;

import manager.ProducerConsumer;
import model.*;
import model.Error;

/**
 * @author Denis
 * @version 1.1
 * The class returns the required data for the requested city from the server.
 */
public class WeatherAPIClient implements Runnable {
    private final CityWeather cityWeather;
    private final ProducerConsumer<JavaFromJson> producerConsumer;

    /**
     * Constructor
     *
     * @param cityWeather      - class-model for data storage
     * @param producerConsumer - class for the implementation of the pattern Producer-Consumer (to exchange data between threads)
     */
    public WeatherAPIClient(final CityWeather cityWeather, final ProducerConsumer<JavaFromJson> producerConsumer) {
        this.cityWeather = cityWeather;
        this.producerConsumer = producerConsumer;
    }

    /**
     * Runs a side thread to get the temperature for the requested city.
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
        String jsonFromServer = httpClient.getData(uri);
        JSONParser jsonParser = new JSONParser();
        if (jsonFromServer != null) {
            JavaFromJson javaFromJson = jsonParser.getJavaFromJson(jsonFromServer);
            try {
                producerConsumer.produce(javaFromJson);
            } catch (InterruptedException ignore) {
            }
        } else {
            JavaErrorFromJson javaErrorFromJson = new JavaErrorFromJson();
            javaErrorFromJson.error = new Error();
            javaErrorFromJson.error.message = "Connection failed!";
            try {
                producerConsumer.produce(javaErrorFromJson);
            } catch (InterruptedException ignore) {
            }
        }
    }
}
