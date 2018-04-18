package com.baryshev.dmitriy.daresayweather.main.presentation.vm

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import com.baryshev.dmitriy.daresayweather.R
import com.baryshev.dmitriy.daresayweather.common.data.exception.WeatherException
import com.baryshev.dmitriy.daresayweather.common.domain.IResourceInteractor
import com.baryshev.dmitriy.daresayweather.common.presentation.BaseVM
import com.baryshev.dmitriy.daresayweather.main.domain.IMainInteractor
import com.baryshev.dmitriy.daresayweather.main.domain.MainData
import com.baryshev.dmitriy.daresayweather.main.presentation.view.CurrentWeatherViewResult
import com.baryshev.dmitriy.daresayweather.main.presentation.view.ForecastViewResult
import com.baryshev.dmitriy.daresayweather.utils.rx.IRxScheduler
import io.reactivex.exceptions.CompositeException
import java.net.UnknownHostException

class MainVM(
    private val rxScheduler: IRxScheduler,
    private val mainInteractor: IMainInteractor,
    private val resourceInteractor: IResourceInteractor
) : BaseVM() {

    val currentWeatherData: MutableLiveData<CurrentWeatherViewResult> = MutableLiveData()
    val forecastData: MutableLiveData<ForecastViewResult> = MutableLiveData()
    val searchStateData: MutableLiveData<Boolean> = MutableLiveData()
    internal var searchText: String = ""

    override fun onCleared() {
        super.onCleared()
        mainInteractor.clear()
    }

    fun loadData(fromCache: Boolean = true, forceLoading: Boolean = true) {

        if (forceLoading || currentWeatherData.value == null || forecastData.value == null) {
            forecastData.value = ForecastViewResult.Progress
            currentWeatherData.value = CurrentWeatherViewResult.Progress
            if (searchStateData.value == false || searchText.isEmpty()) {
                loadCurrentWeather(fromCache)
            } else {
                loadWeatherByCityName()
            }
        }
    }

    fun onSearchTextChanged(text: String) {
        searchText = text
    }

    fun onSearchClick() {
        val value = searchStateData.value
        val isSearchState: Boolean = value != null && !value
        searchStateData.value = isSearchState
    }

    fun onUpdateClick() {
        forecastData.value = ForecastViewResult.Progress
        currentWeatherData.value = CurrentWeatherViewResult.Progress
        loadCurrentWeather(searchStateData.value != true)
    }

    fun onSearchAction() {
        currentWeatherData.value = CurrentWeatherViewResult.Progress
        forecastData.value = ForecastViewResult.Progress
        loadWeatherByCityName()
    }

    @SuppressLint("RxLeakedSubscription")
    private fun loadCurrentWeather(fromCache: Boolean) {
        addDisposable {
            mainInteractor.getCurrentCityWeather(fromCache)
                .subscribeOn(rxScheduler.io())
                .observeOn(rxScheduler.main())
                .subscribe({ weather: MainData.CurrentWeather? ->
                               weather?.let {
                                   searchStateData.value = false
                                   currentWeatherData.value = CurrentWeatherViewResult.Success(it)
                               }
                           }, {
                               currentWeatherData.value =
                                       CurrentWeatherViewResult.Error(getErrorMessageByThrowable(it),
                                                                      it)
                           })
        }

        addDisposable {
            mainInteractor.getCurrentCityForecast(fromCache)
                .subscribeOn(rxScheduler.io())
                .observeOn(rxScheduler.main())
                .subscribe({ forecastWeather: List<MainData.Forecast>? ->
                               forecastWeather?.let {
                                   searchStateData.value = false
                                   forecastData.value = ForecastViewResult.Success(it)
                               }
                           }, {
                               forecastData.value =
                                       ForecastViewResult.Error(getErrorMessageByThrowable(it),
                                                                it)
                           })
        }
    }

    @SuppressLint("RxLeakedSubscription")
    private fun loadWeatherByCityName() {
        addDisposable {
            mainInteractor.getWeatherByCityName(searchText)
                .subscribeOn(rxScheduler.io())
                .observeOn(rxScheduler.main())
                .subscribe({ weather: MainData.CurrentWeather? ->
                               weather?.let {
                                   searchStateData.value = false
                                   currentWeatherData.value = CurrentWeatherViewResult.Success(it)
                               }
                           }, {
                               currentWeatherData.value =
                                       CurrentWeatherViewResult.Error(getErrorMessageByThrowable(it),
                                                                      it)
                           })
        }

        addDisposable {
            mainInteractor.getForecastByCityName(searchText)
                .subscribeOn(rxScheduler.io())
                .observeOn(rxScheduler.main())
                .subscribe({ forecastWeather: List<MainData.Forecast>? ->
                               forecastWeather?.let {
                                   searchStateData.value = false
                                   forecastData.value = ForecastViewResult.Success(it)
                               }
                           }, {
                               forecastData.value =
                                       ForecastViewResult.Error(getErrorMessageByThrowable(it),
                                                                it)
                           })
        }
    }

    private tailrec fun getErrorMessageByThrowable(throwable: Throwable): String {
        return when (throwable) {
            is UnknownHostException -> R.string.error_network.string()
            is WeatherException -> R.string.error_weather.string()
            is CompositeException -> {
                if (!throwable.exceptions.isEmpty()) {
                    getErrorMessageByThrowable(throwable.exceptions[0])
                } else {
                    R.string.error_common.string()
                }
            }
            else -> R.string.error_common.string()
        }
    }

    private fun Int.string(): String = resourceInteractor.getString(this)


}