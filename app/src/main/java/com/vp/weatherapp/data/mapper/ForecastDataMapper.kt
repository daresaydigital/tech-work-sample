package com.vp.weatherapp.data.mapper

import com.vp.weatherapp.data.local.entity.DailyForecastEntity
import com.vp.weatherapp.data.local.entity.HourlyForecastEntity
import com.vp.weatherapp.data.remote.DailyForecastResponse
import com.vp.weatherapp.data.remote.HourlyForecastResponse

fun convertHourly(forecast: HourlyForecastResponse): List<HourlyForecastEntity> {
    val cityId = forecast.city.id
    return forecast.list
            .map { HourlyForecastEntity(0, cityId, it.dt, it.main.temp,
                    it.main.temp_min, it.main.temp_max, it.weather[0].icon,
                    it.weather[0].description) }
}

fun convertDaily(forecast: DailyForecastResponse): List<DailyForecastEntity> {
    val cityId = forecast.city.id
    return forecast.list
            .map { DailyForecastEntity(0, cityId, it.dt, it.temp.day,
                    it.temp.night, it.weather[0].icon, it.weather[0].description) }
}
