package com.vp.weatherapp.data.mapper

import com.vp.weatherapp.data.local.entity.HourlyForecastEntity
import com.vp.weatherapp.data.remote.HourlyForecastResponse


class ForecastDataMapper {

    fun convertRemoteToLocal(forecast: HourlyForecastResponse): List<HourlyForecastEntity> {
        val city = forecast.city.name
        val country = forecast.city.country
        return forecast.list
                .map { HourlyForecastEntity(0, it.dt, city, country, it.main.temp,
                        it.main.temp_min, it.main.temp_max, it.weather[0].icon) }
    }

//    private fun convertForecastListToDomain(list: List<Forecast>):
//            List<ModelForecast> {
//        return list.map { convertForecastItemToDomain(it) }
//    }
//
//    private fun convertForecastItemToDomain(forecast: Forecast): ModelForecast {
//        return ModelForecast(
//                convertDate(forecast.dt),
//                forecast.weather[0].description,
//                forecast.temp.max.toInt().toString(),
//                forecast.temp.day.toInt().toString(),
//                forecast.temp.min.toInt().toString())
//    }
//
//    private fun convertDate(date: Long): String {
//        val df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())
//        return df.format(date * 1000)
//    }
}