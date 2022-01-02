package model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Denis
 * @version 1.0
 * The model class used for JSON parser
 */
public class Current {
    @JsonProperty("temp_c")
    public Double tempC;
    public Condition condition;
    public Double uv;
}
