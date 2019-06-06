package xyz.weather.findlocweather

import android.util.Log

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url
import xyz.weather.findlocweather.modals.CityWeatherResult

class OpenWeatherApi private constructor() {

    internal var retrofit: Retrofit

    init {

        retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    fun getWeather(lat: String, lon: String, weatherResultSuccessCallback: WeatherResultSuccessCallback) {

        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val apiService = retrofit.create<WeatherApiEndpointInterface>(WeatherApiEndpointInterface::class.java!!)

        val endPoint = "weather?lat=$lat&lon=$lon&type=like&cnt=20&units=metric&appid=b4af92bbda4d0b6e66744b4df7fe1422"

        apiService.getWeather(BASE_URL + endPoint).enqueue(object : Callback<CityWeatherResult> {
            override fun onResponse(call: Call<CityWeatherResult>, response: Response<CityWeatherResult>) {

                if (response.isSuccessful) {
                    weatherResultSuccessCallback.onWeatherResultSuccess(response.body())

                } else { // Here I could implement a WeatherResultErrorCallback for informing the UI
                    Log.d("api error :", response.message())
                }

            }

            override fun onFailure(call: Call<CityWeatherResult>, t: Throwable) {
                // Here I could implement a WeatherResultErrorCallback for informing the UI
            }
        })

    }

    interface WeatherApiEndpointInterface {

        @GET
        fun getWeather(@Url url: String): Call<CityWeatherResult>

    }

    interface WeatherResultSuccessCallback {

        fun onWeatherResultSuccess(result: CityWeatherResult?)
    }

    companion object {

        val BASE_URL = "http://api.openweathermap.org/data/2.5/"
        val instance = OpenWeatherApi();
    }


}
