package com.robledo.weatheri.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Weather {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("main")
    @Expose
    private String weatherName;
    @SerializedName("description")
    @Expose
    private String weatherDescription;
    @SerializedName("icon")
    @Expose
    private String icon;

    @SerializedName("id")
    public Integer getId() {
        return id;
    }

    @SerializedName("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @SerializedName("main")
    public String getMain() {
        return weatherName;
    }

    @SerializedName("main")
    public void setMain(String weatherName) {
        this.weatherName = weatherName;
    }

    @SerializedName("description")
    public String getDescription() {
        return weatherDescription;
    }

    @SerializedName("description")
    public void setDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    @SerializedName("icon")
    public String getIcon() {
        return icon;
    }

    @SerializedName("icon")
    public void setIcon(String icon) {
        this.icon = icon;
    }

}
