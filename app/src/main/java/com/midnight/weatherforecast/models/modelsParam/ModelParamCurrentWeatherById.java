package com.midnight.weatherforecast.models.modelsParam;

/**
 *
 */
public class ModelParamCurrentWeatherById {
    private String cityId;
    private String key;

    public ModelParamCurrentWeatherById(String cityId, String key) {
        this.cityId = cityId;
        this.key = key;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
