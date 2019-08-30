package com.example.weatherapp.viewModels

import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.WeatherApplication
import com.example.weatherapp.database.entities.CityWeather
import com.example.weatherapp.models.WeatherData
import com.example.weatherapp.services.WeatherService
import com.example.weatherapp.services.enqueueWithRetry
import com.google.android.gms.location.FusedLocationProviderClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CityViewModel(
    private val weatherService: WeatherService,
    private val cityWeather: CityWeather
) : ViewModel() {

    private val _currentWeather = MutableLiveData<CityWeather>().apply { this.value = cityWeather }
    val currentWeather: LiveData<CityWeather> = _currentWeather

//    private fun fetchUsingCityId(cityId: Long) {
//        val weatherCall = weatherService.getCurrentByCity(cityId, BuildConfig.api_key)
//        weatherCall.enqueueWithRetry(object : Callback<WeatherData> {
//            override fun onFailure(call: Call<WeatherData>, t: Throwable) {
//                // todo handle failure
//            }
//
//            override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
//                val data = response.body()
//                if (data != null) {
//                    _currentWeather.value = data
//                }
//            }
//        })
//    }
//
//    private fun fetchUsingLocation(latitude: Double, longitude: Double) {
//        val weatherCall = weatherService.getCurrentByLocation(latitude, longitude, BuildConfig.api_key)
//        weatherCall.enqueueWithRetry(object : Callback<WeatherData> {
//            override fun onFailure(call: Call<WeatherData>, t: Throwable) {
//                // todo handle failure
//            }
//
//            override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
//                val data = response.body()
//                if (data != null) {
//                    // todo save data to city weather table
////                    val cityId = data.id
////                    sharedPreferences.edit().putLong("cityId", cityId).commit()
//                    //_currentWeather.value = data
//                }
//            }
//        })
//    }


}