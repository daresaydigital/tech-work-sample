package com.example.nejat.weatherapp.POJO;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherResponse {
    @SerializedName("dt_txt")
    private String date; // date text
    @SerializedName("main")
    private Main weatherDetail;
    private Wind wind;
    @SerializedName("weather")
    private List<Weather> weatherDesc;

    public WeatherResponse(String date, Main weatherDetail, Wind wind, List<Weather> weatherDesc) {
        this.date = date;
        this.weatherDetail = weatherDetail;
        this.wind = wind;
        this.weatherDesc = weatherDesc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Main getWeatherDetail() {
        return weatherDetail;
    }

    public void setWeatherDetail(Main weatherDetail) {
        this.weatherDetail = weatherDetail;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public List<Weather> getWeatherDesc() {
        return weatherDesc;
    }

    public void setWeatherDesc(List<Weather> weatherDesc) {
        this.weatherDesc = weatherDesc;
    }
}