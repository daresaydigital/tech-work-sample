package com.example.nejat.weatherapp.POJO;

import com.google.gson.annotations.SerializedName;

public class Main {
    @SerializedName("temp")
    private double temprature;
    private double pressure;
    private double humidity;
    @SerializedName("temp_min")
    private double tempratureMin;
    @SerializedName("temp_max")
    private double tempratureMax;

    public Main(double temprature, double pressure, double humidity, double tempratureMin, double tempratureMax) {
        this.temprature = temprature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.tempratureMin = tempratureMin;
        this.tempratureMax = tempratureMax;
    }

    public double getTemprature() {
        return temprature;
    }

    public void setTemprature(double temprature) {
        this.temprature = temprature;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getTempratureMin() {
        return tempratureMin;
    }

    public void setTempratureMin(double tempratureMin) {
        this.tempratureMin = tempratureMin;
    }

    public double getTempratureMax() {
        return tempratureMax;
    }

    public void setTempratureMax(double tempratureMax) {
        this.tempratureMax = tempratureMax;
    }
}
