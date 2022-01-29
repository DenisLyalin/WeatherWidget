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
    public JavaFromJson getJavaFromJson(final String data) {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaDataFromJson javaDataFromJson;
        JavaErrorFromJson javaErrorFromJson;
        try {
            javaDataFromJson = objectMapper.readValue(data, JavaDataFromJson.class);
            return javaDataFromJson;
        } catch (JsonProcessingException e) {
            try {
                javaErrorFromJson = objectMapper.readValue(data, JavaErrorFromJson.class);
            } catch (JsonProcessingException ex) {
                javaErrorFromJson = new JavaErrorFromJson();
                javaErrorFromJson.error = new Error();
                javaErrorFromJson.error.message = "JSON Parsing failed!";
            }
            return javaErrorFromJson;
        }
    }
}