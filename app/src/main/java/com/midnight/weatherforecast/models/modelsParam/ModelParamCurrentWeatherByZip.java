package com.midnight.weatherforecast.models.modelsParam;

/**
 *
 */
public class ModelParamCurrentWeatherByZip {
    private String zipCode;
    private String key;

    public ModelParamCurrentWeatherByZip(String zipCode, String key) {
        this.zipCode = zipCode;
        this.key = key;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
