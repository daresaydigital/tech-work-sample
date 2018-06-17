package com.ivy.weatherapp.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.ivy.weatherapp.data.local.model.Weather
import com.ivy.weatherapp.data.repository.WeatherRepository
import com.ivy.weatherapp.system.Permissions
import com.ivy.weatherapp.ui.base.BaseViewModel
import com.ivy.weatherapp.util.Failure

class WeatherViewModel(
        weatherRepository: WeatherRepository,
        permissions: Permissions
) : BaseViewModel() {

    val weather: LiveData<Weather> = weatherRepository.getWeather()
    val error: LiveData<Failure> = weatherRepository.getError()
    val permission: MutableLiveData<Boolean> = MutableLiveData()

    init {
        permission.postValue(permissions.hasLocationPermission())
        weatherRepository.fetch()
    }
}