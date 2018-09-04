package com.suroid.weatherapp.models

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class CityWeatherEntity(@PrimaryKey val id: Int,
                             val currentWeather: WeatherModel?,
                             val dailyForecast: List<TemperatureModel> = ArrayList(),
                             val hourlyForecast: List<TemperatureModel> = ArrayList(),
                             @Embedded(prefix = "city") val city: City) {

    override fun equals(other: Any?): Boolean {
        return other is CityWeatherEntity && other.id == id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
