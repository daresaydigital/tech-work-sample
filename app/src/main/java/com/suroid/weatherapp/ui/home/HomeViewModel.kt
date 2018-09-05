package com.suroid.weatherapp.ui.home

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.suroid.weatherapp.BaseViewModel
import com.suroid.weatherapp.models.City
import com.suroid.weatherapp.models.CityWeatherEntity
import com.suroid.weatherapp.models.WeatherModel
import com.suroid.weatherapp.models.remote.ResponseStatus
import com.suroid.weatherapp.repo.CityWeatherRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel : BaseViewModel() {
    /**
     * Injects the required [CityWeatherRepository] in this ViewModel.
     */
    @Inject
    lateinit var cityWeatherRepository: CityWeatherRepository

    val cityWeatherListLiveData: MutableLiveData<ArrayList<CityWeatherEntity>> = MutableLiveData()

    init {
        compositeDisposable.add(cityWeatherRepository.getAllCityWeathers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    onCitiesFetched(it)
                }, {
                    onError(it)
                }))

        compositeDisposable.add(cityWeatherRepository.responseSubject.subscribe { response ->
            when (response) {
                is ResponseStatus.Success -> {
                    cityWeatherListLiveData.value?.let {
                        val pos = it.indexOf(response.data)
                        if (pos > -1) {
                            it[pos] = response.data
                        }
                    }
                }
            }
        })
    }

    private fun onCitiesFetched(cityWeatherList: List<CityWeatherEntity>) {
        cityWeatherListLiveData.value = ArrayList(cityWeatherList)
    }

    private fun onError(t: Throwable?) {
        //TODO handle error case
        Log.d(HomeViewModel::class.java.name, t?.message)
    }

    /**
     * Saves the provided city in WeatherDb
     * @param city city to be added
     */
    fun saveNewCity(city: City) {
        val cityWeather = CityWeatherEntity(id = city.id, city = city, currentWeather = WeatherModel())
        cityWeatherListLiveData.value?.let {
            if (!it.contains(cityWeather)) {
                cityWeatherRepository.saveCityWeather(cityWeather)
                it.add(cityWeather)
                cityWeatherListLiveData.value = it
            }
        }
    }
}