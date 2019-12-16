package com.russellmorris.showweather.data.repository

import com.russellmorris.showweather.data.source.WeatherDataSource
import com.russellmorris.showweather.domain.entity.WeatherEntity
import com.russellmorris.showweather.domain.repository.WeatherRepository
import io.reactivex.Single

class WeatherRepositoryImpl constructor(
    private val weatherDataSource: WeatherDataSource
) : WeatherRepository {

    override fun getWeather(lat: String?, lon: String?, key: String, units: String): Single<WeatherEntity> {
        return weatherDataSource.getWeather(lat, lon, key, units)
    }
}