package com.russellmorris.showweather.service.api

import com.russellmorris.showweather.data.source.WeatherDataSource
import com.russellmorris.showweather.domain.entity.WeatherEntity
import com.russellmorris.showweather.service.dao.mapToDomain
import io.reactivex.Single

class WeatherApiService constructor(
    private val api: WeatherApi
) : WeatherDataSource {

    override fun getWeather(
        latitude: String?,
        longitude: String?,
        key: String,
        units: String
    ): Single<WeatherEntity> {
        return api.getWeather(latitude, longitude, key, units)
            .map { it.mapToDomain() }
    }
}