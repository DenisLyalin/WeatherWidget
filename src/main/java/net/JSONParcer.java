package net;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.*;
import model.Error;

import java.util.ArrayList;

/**
 * @author Denis
 * @version 0.1b
 * Class to conversion JSON in data for JAVA
 */

public class JSONParcer {

    /**
     * Method to conversion JSON to data for JAVA and retrieve temperature value
     */

    public ArrayList<Object> getCityAndTempFromData(final String data) {
        ObjectMapper objectMapper = new ObjectMapper();
        Current current = new Current();
        Location location = new Location();
        ArrayList<Object> cityAndTemp = new ArrayList<>();
        try {
            JsonResponse jsonResponse = objectMapper.readValue(data, JsonResponse.class);
            current = jsonResponse.current;
            location = jsonResponse.location;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        cityAndTemp.add(location.name);
        cityAndTemp.add(current.tempC);
        return cityAndTemp;
    }

    public ArrayList<Object> readError(final String data) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<Object> errorContainer = new ArrayList<>();
        JsonResponseError jsonResponse = objectMapper.readValue(data, JsonResponseError.class);
        Error error = jsonResponse.error;
        errorContainer.add(error);
        return errorContainer;
    }
}