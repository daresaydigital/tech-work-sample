package com.ivy.weatherapp.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.ivy.weatherapp.data.local.model.Weather
import com.ivy.weatherapp.data.repository.WeatherRepository
import com.ivy.weatherapp.system.LocationManager
import com.ivy.weatherapp.system.PermissionManager
import com.ivy.weatherapp.ui.base.BaseViewModel
import com.ivy.weatherapp.util.Failure

class WeatherViewModel(
        private val weatherRepository: WeatherRepository,
        private val locationManager: LocationManager,
        permissionManager: PermissionManager
) : BaseViewModel() {

    val weather: LiveData<Weather> = weatherRepository.getWeather()
    val error: LiveData<Failure> = weatherRepository.getError()
    val permission: MutableLiveData<Boolean> = MutableLiveData()

    init {
        permission.postValue(permissionManager.hasLocationPermission())
    }

    fun fetchWeather() {
        locationManager.getLocation()?.addOnSuccessListener {
            weatherRepository.fetch(it.latitude, it.longitude)
        }
    }
}