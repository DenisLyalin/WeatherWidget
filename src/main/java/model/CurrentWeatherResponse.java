package model;

/**
 * @author Denis
 * @version 1.0
 * The model class used for JSON parser
 */
public class CurrentWeatherResponse extends BaseResponse {
    public Location location;
    public Current current;
}

