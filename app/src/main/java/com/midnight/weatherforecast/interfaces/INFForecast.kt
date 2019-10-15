package com.midnight.weatherforecast.interfaces

import com.midnight.weatherforecast.models.modelsResponse.ModelCurrentWeater
import com.midnight.weatherforecast.models.modelsResponse.ModelForecast

interface INFForecast {
    fun onSuccess(model:ModelForecast)
    fun onFail()
}