package com.baryshev.dmitriy.daresayweather.common.data.weather

import com.baryshev.dmitriy.daresayweather.common.data.network.Api
import com.baryshev.dmitriy.daresayweather.common.data.weather.pojo.ForecastResponse
import com.baryshev.dmitriy.daresayweather.common.data.weather.pojo.WeatherResponse
import io.reactivex.Single

class WeatherRepo(private val api: Api) {

    companion object {
        const val SUCCESS_COD = 200
    }

    fun getCurrentWeatherByCoord(lat: Float, lon: Float): Single<WeatherResponse> {
        return api.getWeatherByCoords(lat, lon)
    }

    fun getCurrentWeatherByCityName(city: String): Single<WeatherResponse> {
        return api.getWeatherByCityName(city)
    }

    fun getForecastWeatherByCoord(lat: Float, lon: Float): Single<ForecastResponse> {
        return api.getForecastWeatherByCoord(lat, lon)
    }

    fun getForecastWeatherByCityName(city: String): Single<ForecastResponse> {
        return api.getForecastWeatherByCityName(city)
    }

}