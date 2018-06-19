package com.ukhanoff.rainbeforeseven.repository

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.ukhanoff.rainbeforeseven.api.WeatherService
import com.ukhanoff.rainbeforeseven.data.WeatherGlobalModel
import com.ukhanoff.rainbeforeseven.data.ForecastWeatherModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class WeatherRepository @Inject constructor(private val weatherService: WeatherService) {

    fun getCurrentWeather(lat:Double, lon:Double): MutableLiveData<WeatherGlobalModel> {
        val data = MutableLiveData<WeatherGlobalModel>()
        weatherService.getCurrentWeather(lat, lon).enqueue(object : Callback<WeatherGlobalModel> {
            override fun onFailure(call: Call<WeatherGlobalModel>?, t: Throwable?) {
                Log.e("WeatherRepository", t.toString())
            }

            override fun onResponse(call: Call<WeatherGlobalModel>, response: Response<WeatherGlobalModel>) {
                // error case is left out for brevity
                data.value = response.body()
                Log.d("WeatherRepository", response.body().toString())
            }
        })
        return data
    }

    fun getForecastWeather(lat:Double, lon:Double): MutableLiveData<ForecastWeatherModel> {
        val data = MutableLiveData<ForecastWeatherModel>()
        weatherService.getForecastWeather(lat, lon).enqueue(object : Callback<ForecastWeatherModel> {
            override fun onFailure(call: Call<ForecastWeatherModel>?, t: Throwable?) {
                Log.e("WeatherRepository", t.toString())
            }

            override fun onResponse(call: Call<ForecastWeatherModel>, response: Response<ForecastWeatherModel>) {
                // error case is left out for brevity
                data.value = response.body()
                Log.d("WeatherRepository", response.body().toString())
            }
        })
        return data
    }

}
