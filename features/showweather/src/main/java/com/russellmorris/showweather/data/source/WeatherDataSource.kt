package com.russellmorris.showweather.data.source

import com.russellmorris.showweather.domain.entity.WeatherEntity
import io.reactivex.Single

interface WeatherDataSource {
    fun getWeather(latitude: String?, longitude: String?, key: String, units: String): Single<WeatherEntity>
}