package com.midnight.weatherforecast.models.modelsSub;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelCloud {
    @SerializedName("all")
    @Expose
    private Integer all;

    public Integer getAll() {
        return all;
    }

    public void setAll(Integer all) {
        this.all = all;
    }
}
