package com.midnight.weatherforecast.models.modelsParam;

/**
 *
 */
public class ModelParamCurrentWeatherByName {
    private String cityName;
    private String key;

    public ModelParamCurrentWeatherByName(String cityName, String key) {
        this.cityName = cityName;
        this.key = key;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
