package com.russellmorris.showweather.domain.repository

import com.russellmorris.showweather.domain.entity.WeatherEntity
import io.reactivex.Single

interface WeatherRepository {
    fun getWeather(lat: String?, lon: String?, key: String, units: String): Single<WeatherEntity>
}