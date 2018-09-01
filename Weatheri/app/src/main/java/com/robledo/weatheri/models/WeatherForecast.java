package com.robledo.weatheri.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherForecast {
    @SerializedName("list")
    @Expose
    private java.util.List<List> listDailyWeather = null;

    public java.util.List<List> getList() {
        return listDailyWeather;
    }

    public void setList(java.util.List<List> listDailyWeather) {
        this.listDailyWeather = listDailyWeather;
    }
}
