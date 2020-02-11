package com.sneha.weatherapp.ui.location

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.location.Location
import androidx.lifecycle.LiveData
import com.google.android.gms.location.*
import com.sneha.weatherapp.data.local.prefs.UserPreferences
import com.sneha.weatherapp.data.model.LocationData

class LocationLiveData(context: Application) :
    LiveData<LocationData>() {

    private var fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private var sharedPreference =
        context.getSharedPreferences("weather-app-prefs", Context.MODE_PRIVATE)

    override fun onInactive() {
        super.onInactive()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }


    @SuppressLint("MissingPermission")
    override fun onActive() {
        super.onActive()
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.also {
                    setLocationData(it)
                }
            }
        startLocationUpdates()
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null
        )
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult ?: return
            for (location in locationResult.locations) {
                setLocationData(location)
            }
        }
    }

    private fun setLocationData(location: Location) {
        value = LocationData(
            longitude = location.longitude,
            latitude = location.latitude
        )

        val userPreferences = UserPreferences(sharedPreference)
        userPreferences.setLatitude(location.latitude.toString())
        userPreferences.setLongitude(location.longitude.toString())
    }

    companion object {
        val locationRequest: LocationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }
}