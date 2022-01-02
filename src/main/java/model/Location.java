package model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Denis
 * @version 1.0
 * The model class used for JSON parser
 */
public class Location {
    public String name;
    public String region;
    public String country;
    public Double lat;
    public Double lon;
    @JsonProperty("tz_id")
    public String tzId;
    @JsonProperty("localtime_epoch")
    public Integer localtimeEpoch;
    public String localtime;
}