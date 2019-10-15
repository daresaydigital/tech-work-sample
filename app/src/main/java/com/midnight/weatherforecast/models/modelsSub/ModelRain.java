package com.midnight.weatherforecast.models.modelsSub;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelRain {
    @SerializedName("1h")
    @Expose
    private Double _1h;

    public Double get_1h() {
        return _1h;
    }

    public void set_1h(Double _1h) {
        this._1h = _1h;
    }
}
