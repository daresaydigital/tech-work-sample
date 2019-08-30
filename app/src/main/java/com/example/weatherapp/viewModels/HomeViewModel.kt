package com.example.weatherapp.viewModels

import androidx.lifecycle.*
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


class HomeViewModel(private val cityWeatherDao: CityWeatherDao, private val weatherService: WeatherService) :
    ViewModel() {
    // pull list of saved cities with weather saved
    val _listOfCityWithWeather = MutableLiveData<List<CityWeather>>()
    val listOfCityWithWeather: LiveData<List<CityWeather>> get() = _listOfCityWithWeather
    // after selection from ui
    private val _selectedCityWeather = MediatorLiveData<CityWeather>()
    val selectedCityWeather: LiveData<CityWeather> get() = _selectedCityWeather

    //
    private var latestFetchedCityId: Long? = null
    val selectedPosition: LiveData<Int> = Transformations.switchMap(listOfCityWithWeather) {
        val position = it.indexOfFirst {
            it.city.id == latestFetchedCityId
        }
        MutableLiveData<Int>().apply { value = position }
    }


    init {
        runOnIoThread { _listOfCityWithWeather.postValue(cityWeatherDao.getAll()) }
    }


    fun select(position: Int) {
        _selectedCityWeather.value = listOfCityWithWeather.value?.takeIf { it.size > 0 }?.get(position)
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
                    latestFetchedCityId = city.id
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
                        _listOfCityWithWeather.postValue(cityWeatherDao.getAll())
                    }
                }
            }
        })
    }

    fun fetchWeatherForLocation(latitude: Double, longitude: Double) {
        val weatherCall = weatherService.getCurrentByLocation(latitude, longitude, BuildConfig.api_key)
        weatherCall.enqueueWithRetry(object : Callback<WeatherData> {
            override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                // todo handle failure
            }

            override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                val data = response.body()
                if (data != null) {
                    val city = City(data.id, data.name, data.sys.country, data.timezone)
                    latestFetchedCityId = city.id
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
                        _listOfCityWithWeather.postValue(cityWeatherDao.getAll())
                    }
                }
            }
        })

    }
}