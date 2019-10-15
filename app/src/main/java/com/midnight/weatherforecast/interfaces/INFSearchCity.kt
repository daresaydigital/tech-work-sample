package com.midnight.weatherforecast.interfaces

import com.midnight.weatherforecast.models.modelsResponse.ModelCurrentWeater

interface INFSearchCity {
    fun onSuccess(model:ModelCurrentWeater)
    fun onFail()
}