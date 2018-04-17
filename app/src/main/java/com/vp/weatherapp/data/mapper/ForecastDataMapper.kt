package com.vp.weatherapp.data.mapper

import com.vp.weatherapp.data.local.entity.DailyForecastEntity
import com.vp.weatherapp.data.local.entity.HourlyForecastEntity
import com.vp.weatherapp.data.remote.DailyForecastResponse
import com.vp.weatherapp.data.remote.HourlyForecastResponse


class ForecastDataMapper {

    fun convertRemoteToLocal(forecast: HourlyForecastResponse): List<HourlyForecastEntity> {
        val city = forecast.city.name
        val country = forecast.city.country
        return forecast.list
                .map { HourlyForecastEntity(0, it.dt, city, country, it.main.temp,
                        it.main.temp_min, it.main.temp_max, it.weather[0].icon) }
    }

    fun convertRemoteToLocal(forecast: DailyForecastResponse): List<DailyForecastEntity> {
        val city = forecast.city.name
        val country = forecast.city.country
        return forecast.list
                .map { DailyForecastEntity(0, it.dt, city, country, it.temp.day,
                        it.temp.night, it.weather[0].icon) }
    }

}