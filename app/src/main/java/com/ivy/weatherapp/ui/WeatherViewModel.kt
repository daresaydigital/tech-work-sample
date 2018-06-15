package com.ivy.weatherapp.ui

import com.ivy.weatherapp.data.repository.WeatherRepository
import com.ivy.weatherapp.ui.base.BaseViewModel

class WeatherViewModel(
        private val weatherRepository: WeatherRepository
) : BaseViewModel() {

}