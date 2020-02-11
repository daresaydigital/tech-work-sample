package com.sneha.weatherapp.ui.location

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.sneha.weatherapp.data.local.prefs.UserPreferences
import com.sneha.weatherapp.data.repository.UserRepository
import javax.inject.Inject

class LocationViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val locationData = LocationLiveData(application)

    fun getLocationData() = locationData

    val gpsStatusLiveData = GpsStatusLiveData(application)

}
