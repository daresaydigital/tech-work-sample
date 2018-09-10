package com.example.nejat.weatherapp.POJO;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Forecast {
    @SerializedName("list")
    private List<WeatherResponse> list;
    @SerializedName("cnt")
    private int count;
    private City city;

    public Forecast(List<WeatherResponse> list, int count, City city) {
        this.list = list;
        this.count = count;
        this.city = city;
    }

    public List<WeatherResponse> getList() {
        return list;
    }

    public void setList(List<WeatherResponse> list) {
        this.list = list;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}