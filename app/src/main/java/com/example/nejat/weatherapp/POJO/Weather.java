package com.example.nejat.weatherapp.POJO;

import com.google.gson.annotations.SerializedName;

public class Weather {
    @SerializedName("main")
    private String weatherFlow;
    private String description;
    @SerializedName("id")
    private int icon;

    public Weather(String weatherFlow, String description, int icon) {

        this.weatherFlow = weatherFlow;
        this.description = description;
        this.icon = icon;
    }



    public String getWeatherFlow() {
        return weatherFlow;
    }

    public void setWeatherFlow(String weatherFlow) {
        this.weatherFlow = weatherFlow;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

}
