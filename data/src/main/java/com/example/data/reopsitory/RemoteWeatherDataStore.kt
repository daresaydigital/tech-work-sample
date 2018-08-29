package com.example.data.reopsitory

import com.example.data.api.Api
import com.example.domain.WeatherDataStore
import com.example.domain.model.DailyForecastResponse
import com.example.domain.model.ForecastResponse
import com.example.domain.model.WeatherResponse
import io.reactivex.Observable


class RemoteWeatherDataStore(private val api: Api) : WeatherDataStore {

    override fun getWeatherByCoord(lat: String, lon: String): Observable<WeatherResponse> {
        return api.getWeatherByCoord(lat, lon)
    }

    override fun getWeather(city: String): Observable<WeatherResponse> {
        return api.getWeatherNew(city)
    }

    override fun getForecastByCoord(lat: String, lon: String): Observable<ForecastResponse> {
        return api.getForecastByCoord(lat,lon)
    }

    override fun getDailyForecastByCoord(lat: String, lon: String, days : Int): Observable<DailyForecastResponse> {
        return api.getDailyForecastByCoord(lat,lon, days)
    }

}