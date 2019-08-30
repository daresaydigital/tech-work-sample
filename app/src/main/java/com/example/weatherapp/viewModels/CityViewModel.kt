package com.example.weatherapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.database.daos.CityWeatherDao
import com.example.weatherapp.database.entities.City
import com.example.weatherapp.database.entities.CityWeather
import com.example.weatherapp.models.Weather
import com.example.weatherapp.models.WeatherData
import com.example.weatherapp.services.WeatherService
import com.example.weatherapp.services.enqueueWithRetry
import com.example.weatherapp.utils.runOnIoThread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class CityViewModel(
    private val weatherService: WeatherService,
    private val cityWeatherDao: CityWeatherDao,
    private val cityWeather: CityWeather
) : ViewModel() {


    private val _currentWeather = MutableLiveData<CityWeather>().apply { this.value = cityWeather }
    val currentWeather: LiveData<CityWeather> = _currentWeather

    // minutes
    val diffMinutes = MutableLiveData<Long>()

    private val timer = Timer()

    init {
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                updateDiff()
            }
        }, 0, ONE_SECOND)

        val lastUpdateMillis = _currentWeather.value?.lastUpdateClient
        val cityId = _currentWeather.value?.id
        if (lastUpdateMillis != null && cityId != null) {
            val millisSinceLastUpdate = (Date().time - lastUpdateMillis)
            if (millisSinceLastUpdate > REFRESH_THRESHOLD) {
                fetchUsingCityId(cityId)
            }
        }
    }


    private fun updateDiff() {
        val lastUpdateMillis = _currentWeather.value?.lastUpdateClient
        if (lastUpdateMillis != null) {
            val nowSeconds = Date().time
            val minutesDiff = (nowSeconds - lastUpdateMillis) / (1000 * 60)
            diffMinutes.postValue(minutesDiff)
        }
    }


    private fun updateDiffServer() {
        val lastUpdateEpoch = _currentWeather.value?.lastUpdateServer
        if (lastUpdateEpoch != null) {
            val nowSeconds = Calendar.getInstance().apply {
                timeZone = TimeZone.getTimeZone("gmt")
            }.timeInMillis / 1000
            val minutesDiff = (nowSeconds - lastUpdateEpoch) / 60
            diffMinutes.postValue(minutesDiff)
        }
    }

    fun fetchUsingCityId(cityId: Long) {
        val weatherCall = weatherService.getCurrentByCity(cityId, BuildConfig.api_key)
        weatherCall.enqueueWithRetry(object : Callback<WeatherData> {
            override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                // todo handle failure
            }

            override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                val data = response.body()
                if (data != null) {
                    val city = City(data.id, data.name, data.sys.country, data.timezone)

                    val weather = Weather(
                        data.weather.first().main,
                        data.weather.first().description,
                        data.weather.first().icon,
                        data.main.pressure,
                        data.main.humidity,
                        data.main.temp,
                        data.main.temp_min,
                        data.main.temp_max,
                        data.weather.first().id
                    )

                    val cityWeather = CityWeather(city.id, weather, Date().time, data.dt, city)
                    runOnIoThread {
                        cityWeatherDao.insertOrUpdate(cityWeather)
                        _currentWeather.postValue(cityWeather)
                    }
                }
            }
        })
    }

    companion object {

        const val ONE_SECOND: Long = 1000
        const val ONE_MINUTE: Long = 60 * ONE_SECOND
        const val ONE_HOUR: Long = 60 * ONE_MINUTE
        const val REFRESH_THRESHOLD = 4 * ONE_HOUR
    }

}