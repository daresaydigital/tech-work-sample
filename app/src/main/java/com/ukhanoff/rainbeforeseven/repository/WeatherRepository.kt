package com.ukhanoff.rainbeforeseven.repository

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.ukhanoff.rainbeforeseven.api.WeatherService
import com.ukhanoff.rainbeforeseven.model.CurrentWeatherModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class WeatherRepository @Inject constructor(private val weatherService: WeatherService) {

    fun getCurrentWeather(lat:Double, lon:Double): MutableLiveData<CurrentWeatherModel> {
        // This is not an optimal implementation, we'll fix it below
        val data = MutableLiveData<CurrentWeatherModel>()
        weatherService.getCurrentWeather(lat, lon).enqueue(object : Callback<CurrentWeatherModel> {
            override fun onFailure(call: Call<CurrentWeatherModel>?, t: Throwable?) {
                Log.e("WeatherRepository", t.toString())
            }

            override fun onResponse(call: Call<CurrentWeatherModel>, response: Response<CurrentWeatherModel>) {
                // error case is left out for brevity
                data.value = response.body()
                Log.d("WeatherRepository", response.body().toString())
            }
        })
        return data
    }

}
