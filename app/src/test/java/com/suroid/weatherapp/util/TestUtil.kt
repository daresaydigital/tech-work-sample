package com.suroid.weatherapp.util

import com.google.gson.Gson
import com.suroid.weatherapp.models.City
import com.suroid.weatherapp.models.CityWeatherEntity
import com.suroid.weatherapp.models.TemperatureModel
import com.suroid.weatherapp.models.WeatherModel
import com.suroid.weatherapp.models.remote.*
import org.mockito.ArgumentMatcher
import java.util.function.Predicate

val gson = Gson()

fun createWeatherResponseModel(id: Int): WeatherResponseModel {
    val main = Main(temp = 1.0f,
            humidity = 2,
            temp_max = 3.0f,
            temp_min = 4.0f
    )
    val weather = Weather(id = 1,
            main = "main",
            description = "description",
            icon = "icon")
    val wind = Wind(speed = 1.0f)
    val sys = Sys(country = "country")
    return WeatherResponseModel(main = main,
            weather = ArrayList<Weather>().apply { add(weather) },
            wind = wind,
            dt = 0,
            id = id,
            name = "name",
            sys = sys)
}

fun createWeatherResponseModel() = createWeatherResponseModel(123)

fun createCity() = City(name = "name", country = "country", id = 0)

fun createCityWeather() = CityWeatherEntity(123,
        WeatherModel("title", "description", TemperatureModel(1.1f, 2.2f, 3.3f), 4f, 5, 6),
        city = createCity())


fun <T> matches(predicate: Predicate<T>): ArgumentMatcher<T> {
    return ArgumentMatcher<T> { argument -> predicate.test(argument as T) }
}