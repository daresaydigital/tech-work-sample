package com.suroid.weatherapp.ui.weathercards

import android.arch.lifecycle.MutableLiveData
import com.suroid.weatherapp.viewmodel.BaseViewModel
import com.suroid.weatherapp.models.CityWeatherEntity
import com.suroid.weatherapp.models.remote.ResponseStatus
import com.suroid.weatherapp.repo.CityWeatherRepository
import com.suroid.weatherapp.utils.Mockable
import com.suroid.weatherapp.utils.weatherIconForId
import com.suroid.weatherapp.utils.weatherImageForId
import javax.inject.Inject

/**
 * @Inject Injects the required [CityWeatherRepository] in this ViewModel.
 */
@Mockable
class WeatherCardViewModel @Inject constructor(private val cityWeatherRepository: CityWeatherRepository): BaseViewModel() {

    val loadingStatus: MutableLiveData<Boolean> = MutableLiveData()
    val temp: MutableLiveData<String> = MutableLiveData()
    val city: MutableLiveData<String> = MutableLiveData()
    val weatherTitle: MutableLiveData<String> = MutableLiveData()
    val humidity: MutableLiveData<String> = MutableLiveData()
    val minMaxTemp: MutableLiveData<String> = MutableLiveData()
    val wind: MutableLiveData<String> = MutableLiveData()
    val icon: MutableLiveData<Int> = MutableLiveData()
    val image: MutableLiveData<Int> = MutableLiveData()

    fun setupWithCity(cityWeather: CityWeatherEntity) {
        updateValues(cityWeather)
        compositeDisposable.add(cityWeatherRepository.responseSubject.subscribe { response ->
            if (response.tag is CityWeatherEntity && response.tag == cityWeather) {
                when (response) {
                    is ResponseStatus.Progress -> {
                        loadingStatus.value = response.loading
                    }
                    is ResponseStatus.Success -> {
                        updateValues(response.data)
                    }
                    is ResponseStatus.Failure -> {
                        //TODO handle failure here
                    }
                }
            }
        })

        cityWeatherRepository.fetchWeatherOfCity(cityWeather)
    }

    private fun updateValues(cityWeather: CityWeatherEntity) {
        cityWeather.currentWeather?.let {
            it.temperature?.let { temperature ->
                temp.value = temperature.temp?.toInt().toString()
                minMaxTemp.value = "${temperature.minTemp?.toInt()}/${temperature.maxTemp?.toInt()}"
            }
            humidity.value = it.humidity?.toString()
            wind.value = it.windSpeed?.toString()
            weatherTitle.value = it.title
            icon.value = weatherIconForId(it.weather_id ?: 0)
            image.value = weatherImageForId(it.weather_id ?: 0)

        }
        city.value = "${cityWeather.city.name}, ${cityWeather.city.country}"
    }

}