package com.example.nejat.weatherapp.POJO;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListData {
    private String date;
    private double temp;
    private double speed;
    private String weather_desc;
    private int icon;
    private double temp_max;
    private double temp_min;
    private String cityName;

    public ListData(String date, double temp, double speed, String weather_desc, int icon, double temp_max, double temp_min,String cityName) {
        this.date = date;
        this.temp = temp;
        this.speed = speed;
        this.weather_desc = weather_desc;
        this.icon = icon;
        this.temp_max = temp_max;
        this.temp_min = temp_min;
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public double getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(double temp_max) {
        this.temp_max = temp_max;
    }

    public double getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(double temp_min) {
        this.temp_min = temp_min;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public String getWeather_desc() {
        return weather_desc;
    }

    public void setWeather_desc(String weather_desc) {
        this.weather_desc = weather_desc;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
