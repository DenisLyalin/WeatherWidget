package model;

import com.fasterxml.jackson.annotation.JsonProperty;

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