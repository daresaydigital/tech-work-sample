package com.suroid.weatherapp.ui.home

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.location.Location
import android.util.Log
import com.suroid.weatherapp.BaseViewModel
import com.suroid.weatherapp.R
import com.suroid.weatherapp.models.City
import com.suroid.weatherapp.models.CityWeatherEntity
import com.suroid.weatherapp.models.WeatherModel
import com.suroid.weatherapp.models.remote.ResponseStatus
import com.suroid.weatherapp.repo.CityWeatherRepository
import com.suroid.weatherapp.utils.showToast
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
    val loading: MutableLiveData<Boolean> = MutableLiveData()

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
                is ResponseStatus.Progress -> {
                    if (response.tag == UNKNOWN_CITY) {
                        loading.value = response.loading
                    }
                }
                is ResponseStatus.Success -> {
                    cityWeatherListLiveData.value?.let {
                        val pos = it.indexOf(response.data)
                        if (pos > -1) {
                            it[pos] = response.data
                        } else {
                            it.add(response.data)
                            cityWeatherListLiveData.value = it
                        }
                    }
                }
                is ResponseStatus.Failure -> {
                    if (response.tag == UNKNOWN_CITY) {
                        showToast(R.string.cannot_find_location)
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


    /**
     * Saves the provided city in WeatherDb
     * @param city city to be added
     */
    @SuppressLint("MissingPermission")
    fun fetchForCurrentLocation(location: Location) {
        cityWeatherRepository.fetchWeatherWithLatLong(location.latitude, location.longitude, UNKNOWN_CITY)

    }

    companion object {
        const val UNKNOWN_CITY = -1
    }
}