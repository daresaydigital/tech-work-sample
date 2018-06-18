package com.ukhanoff.rainbeforeseven.repository

import android.arch.lifecycle.MutableLiveData
import com.ukhanoff.rainbeforeseven.api.WeatherService
import com.ukhanoff.rainbeforeseven.model.CurrentWeatherModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class WeatherRepository {
    private lateinit var weatherService: WeatherService

    fun getCurrentWeather(): MutableLiveData<CurrentWeatherModel> {
        // This is not an optimal implementation, we'll fix it below
        val data = MutableLiveData<CurrentWeatherModel>()
        weatherService.getCurrentWeather(30.22, 122.30).enqueue(object : Callback<CurrentWeatherModel> {
            override fun onFailure(call: Call<CurrentWeatherModel>?, t: Throwable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<CurrentWeatherModel>, response: Response<CurrentWeatherModel>) {
                // error case is left out for brevity
                data.value = response.body()
            }
        })
        return data
    }
}

