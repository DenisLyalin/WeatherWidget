package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Current {
    @JsonProperty("temp_c")
    public Double tempC;
    public Condition condition;
    public Double uv;
}
