package xing.challenge.rahulmohan.repository.datasource

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import sample.network.rahul.android_weather_app.datasource.data.WeatherResponse


/**
 * Created by rahul
 */
interface WeatherService {

    @GET("/weather?q=Cochin,IN&key=62fc4256-8f8c-11e5-8994-feff819cdc9f")
    fun getWeather(): Call<WeatherResponse>
}