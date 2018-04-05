package xing.challenge.rahulmohan.repository.datasource

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import sample.network.rahul.android_weather_app.datasource.data.WeatherResponse
import sample.network.rahul.android_weather_app.util.Utils


/**
 * Created by rahul
 */
interface WeatherService {

    @GET("/weather?key=" + Utils.API_KEY)
    fun getWeather(@Query("lat") lat:Double ,@Query("lon") lon:Double ): Call<WeatherResponse>
}