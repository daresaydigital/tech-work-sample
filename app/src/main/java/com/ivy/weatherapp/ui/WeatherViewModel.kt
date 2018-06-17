package com.ivy.weatherapp.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.ivy.weatherapp.data.local.model.Weather
import com.ivy.weatherapp.data.repository.WeatherRepository
import com.ivy.weatherapp.system.Permissions
import com.ivy.weatherapp.ui.base.BaseViewModel

class WeatherViewModel(
        private val weatherRepository: WeatherRepository,
        private val permissions: Permissions
) : BaseViewModel() {

    val weather: LiveData<Weather> = weatherRepository.get()
    val permission: MutableLiveData<Boolean> = MutableLiveData()

    init {
        permission.postValue(permissions.hasLocationPermission())
    }
}