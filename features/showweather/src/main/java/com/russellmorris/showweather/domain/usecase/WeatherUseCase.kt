package com.russellmorris.showweather.domain.usecase

import com.russellmorris.showweather.domain.entity.WeatherEntity
import com.russellmorris.showweather.domain.repository.WeatherRepository
import io.reactivex.Single

class WeatherUseCase constructor(private val weatherRepository: WeatherRepository) {
    fun getWeather(lat: String?, lon: String?, key: String, units: String): Single<WeatherEntity> =
        weatherRepository.getWeather(lat, lon, key, units)
}