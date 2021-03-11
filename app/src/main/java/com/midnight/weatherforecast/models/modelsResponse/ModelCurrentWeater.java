package com.midnight.weatherforecast.models.modelsResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.midnight.weatherforecast.models.modelsSub.ModelCloud;
import com.midnight.weatherforecast.models.modelsSub.ModelCoord;
import com.midnight.weatherforecast.models.modelsSub.ModelMain;
import com.midnight.weatherforecast.models.modelsSub.ModelRain;
import com.midnight.weatherforecast.models.modelsSub.ModelSys;
import com.midnight.weatherforecast.models.modelsSub.ModelWeather;
import com.midnight.weatherforecast.models.modelsSub.ModelWind;

import java.io.Serializable;
import java.util.List;

public class ModelCurrentWeater implements Serializable {
    @SerializedName("coord")
    @Expose
    private ModelCoord coord;
    @SerializedName("weather")
    @Expose
    private List<ModelWeather> weather = null;
    @SerializedName("base")
    @Expose
    private String base;
    @SerializedName("main")
    @Expose
    private ModelMain main;
    @SerializedName("visibility")
    @Expose
    private Integer visibility;
    @SerializedName("wind")
    @Expose
    private ModelWind wind;
    @SerializedName("rain")
    @Expose
    private ModelRain rain;
    @SerializedName("clouds")
    @Expose
    private ModelCloud clouds;
    @SerializedName("dt")
    @Expose
    private Integer dt;
    @SerializedName("sys")
    @Expose
    private ModelSys sys;
    @SerializedName("timezone")
    @Expose
    private Integer timezone;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("cod")
    @Expose
    private Integer cod;

    public ModelCoord getCoord() {
        return coord;
    }

    public void setCoord(ModelCoord coord) {
        this.coord = coord;
    }

    public List<ModelWeather> getWeather() {
        return weather;
    }

    public void setWeather(List<ModelWeather> weather) {
        this.weather = weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public ModelMain getMain() {
        return main;
    }

    public void setMain(ModelMain main) {
        this.main = main;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public ModelWind getWind() {
        return wind;
    }

    public void setWind(ModelWind wind) {
        this.wind = wind;
    }

    public ModelRain getRain() {
        return rain;
    }

    public void setRain(ModelRain rain) {
        this.rain = rain;
    }

    public ModelCloud getClouds() {
        return clouds;
    }

    public void setClouds(ModelCloud clouds) {
        this.clouds = clouds;
    }

    public Integer getDt() {
        return dt;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }

    public ModelSys getSys() {
        return sys;
    }

    public void setSys(ModelSys sys) {
        this.sys = sys;
    }

    public Integer getTimezone() {
        return timezone;
    }

    public void setTimezone(Integer timezone) {
        this.timezone = timezone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }
}
