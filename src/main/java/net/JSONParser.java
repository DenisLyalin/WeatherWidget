package net;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.*;
import model.Error;

/**
 * @author Denis
 * @version 1.1
 * Class to convert JSON in data for JAVA
 */

public class JSONParser {

    /**
     * Method to convert JSON to data for JAVA and retrieve temperature value
     *
     * @param data - a data to convert
     * @return returns data or error object for JAVA
     */
    public BaseResponse parseCurrentWeatherResponse(final String data) {
        ObjectMapper objectMapper = new ObjectMapper();
        CurrentWeatherResponse currentWeatherResponse;
        ErrorResponse errorResponse;
        try {
            currentWeatherResponse = objectMapper.readValue(data, CurrentWeatherResponse.class);
            return currentWeatherResponse;
        } catch (JsonProcessingException e) {
            try {
                errorResponse = objectMapper.readValue(data, ErrorResponse.class);
            } catch (JsonProcessingException ex) {
                errorResponse = new ErrorResponse();
                errorResponse.error = new Error();
                errorResponse.error.message = "JSON Parsing failed!";
            }
            return errorResponse;
        }
    }
}