package sample.network.rahul.android_weather_app.datasource.remote

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.location.Location
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sample.network.rahul.android_weather_app.datasource.data.WeatherResponse


class WeatherClient {

    private var weatherService: WeatherService
    private var BASE_URL = "http://worksample-api.herokuapp.com/"

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        weatherService = retrofit.create(WeatherService::class.java)
    }

    companion object {
        private val weatherClient: WeatherClient = WeatherClient()
        @Synchronized
        fun getInstance(): WeatherClient {
            return weatherClient
        }
    }

    fun getWeather(location: Location): LiveData<WeatherResponse> {
        val data = MutableLiveData<WeatherResponse>()
        weatherService.getWeather(location.latitude,location.longitude).enqueue(object : Callback<WeatherResponse> {
            override fun onFailure(call: Call<WeatherResponse>?, t: Throwable?) {
                Log.d("network response", t.toString())
            }

            override fun onResponse(call: Call<WeatherResponse>?, response: Response<WeatherResponse>) {
                data.value = response.body()
            }

        })
        return data
    }
}