package com.midnight.weatherforecast.controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class ControllerLocation implements LocationListener {
    private LocationManager locationManager;
    private int TIME_INTERVAL = 100;
    private int DISTANCE_INTERVAL = 1;
    private String TAG="ControllerLocation";
    public LocationManager getLocationManager() {
        return locationManager;
    }
    private LocationListener delegate;

    public ControllerLocation(Activity activity,LocationListener delegate) {
        this.locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        this.delegate=delegate;
    }

    @SuppressLint("MissingPermission")
    public void requestUpdates(){
        this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, TIME_INTERVAL, DISTANCE_INTERVAL, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "Location found : " + location.getLatitude() + ", " + location.getLongitude() + (location.hasAccuracy() ? " : +- " + location.getAccuracy() + " meters" : ""));
        this.delegate.onLocationChanged(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d(TAG, "Provided status changed : " + provider + " : status : " + status);
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d(TAG, "Provider enabled : " + provider);
        this.delegate.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d(TAG, "Provider disabled : " + provider);
        this.delegate.onProviderDisabled(provider);
    }
}
