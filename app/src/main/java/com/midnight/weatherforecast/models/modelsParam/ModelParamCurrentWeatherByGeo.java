package com.midnight.weatherforecast.models.modelsParam;

/**
 *
 */
public class ModelParamCurrentWeatherByGeo {
    private String lat;
    private String lon;
    private String key;

    public ModelParamCurrentWeatherByGeo(String lat, String lon, String key) {
        this.lat = lat;
        this.lon = lon;
        this.key = key;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
