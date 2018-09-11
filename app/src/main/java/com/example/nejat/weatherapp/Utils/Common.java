package com.example.nejat.weatherapp.Utils;

import android.app.Application;

public class Common extends Application {
    double lat;
    double lon;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
