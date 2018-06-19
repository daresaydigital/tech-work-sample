package com.ukhanoff.rainbeforeseven.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.location.Location
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.ukhanoff.rainbeforeseven.data.ForecastWeatherModel
import com.ukhanoff.rainbeforeseven.data.WeatherGlobalModel
import com.ukhanoff.rainbeforeseven.location.UserLocationManager
import com.ukhanoff.rainbeforeseven.repository.WeatherRepository
import javax.inject.Inject


class WeatherViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {

    fun getCurrentWeather(): MutableLiveData<WeatherGlobalModel> {
        return repository.weatherCurrentData
    }

    fun getForecastWeather(): MutableLiveData<ForecastWeatherModel> {
        return repository.weatherForecastData
    }

    private fun getCurrentWeather(lat: Double, lon: Double): MutableLiveData<WeatherGlobalModel> {
        return repository.getCurrentWeather(lat, lon)
    }

    private fun getForecastWeather(lat: Double, lon: Double): MutableLiveData<ForecastWeatherModel> {
        return repository.getForecastWeather(lat, lon)
    }

    fun getWeather(context: Context) {
        getUserLocation(context)?.addOnSuccessListener {
            if (it == null) {
                Toast.makeText(context, "Please turn your GPS to use app", Toast.LENGTH_LONG).show()
            } else {
                getCurrentWeather(it.latitude, it.longitude)
                getForecastWeather(it.latitude, it.longitude)
            }
        }
    }

    private fun getUserLocation(context: Context): Task<Location>? {
        return UserLocationManager(context).getCurrentLocation()
    }

}
