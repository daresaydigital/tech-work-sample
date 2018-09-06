package com.suroid.weatherapp.util

import com.suroid.weatherapp.models.City
import com.suroid.weatherapp.models.CityWeatherEntity
import com.suroid.weatherapp.models.TemperatureModel
import com.suroid.weatherapp.models.WeatherModel
import com.suroid.weatherapp.models.remote.WeatherResponseModel
import org.mockito.Mockito

/**
 * a kotlin friendly mock that handles generics
 */
inline fun <reified T> mock(): T = Mockito.mock(T::class.java)


/**
 * Extension function to push the loading status to the observing responseStatus
 * */
fun WeatherResponseModel.mapToWeatherEntityTest(date: Long): CityWeatherEntity {
    val temperatureModel = TemperatureModel(main.temp, main.temp_min, main.temp_max)
    val weatherModel = WeatherModel(getWeather()?.main, getWeather()?.description, temperatureModel, wind.speed, main.humidity, getWeather()?.id)
    val city = City(name, sys.country, id)
    return CityWeatherEntity(id, weatherModel, city = city, date = date)
}


