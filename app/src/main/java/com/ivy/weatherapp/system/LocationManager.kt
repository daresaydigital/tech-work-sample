package com.ivy.weatherapp.system

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task

interface LocationManager {
    fun getLocation(): Task<Location>?
}

class LocationManagerImpl(
        context: Context,
        private val locationClient: FusedLocationProviderClient? = LocationServices.getFusedLocationProviderClient(context)
) : LocationManager {

    @SuppressLint("MissingPermission")
    override fun getLocation() = locationClient?.lastLocation
}