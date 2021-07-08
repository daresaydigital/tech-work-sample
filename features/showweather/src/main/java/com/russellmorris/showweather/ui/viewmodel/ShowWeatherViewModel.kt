package com.russellmorris.showweather.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.russellmorris.extensions.Resource
import com.russellmorris.extensions.setError
import com.russellmorris.extensions.setLoading
import com.russellmorris.extensions.setSuccess
import com.russellmorris.showweather.BuildConfig
import com.russellmorris.showweather.domain.usecase.WeatherUseCase
import com.russellmorris.showweather.ui.model.Weather
import com.russellmorris.showweather.ui.model.mapToPresentation
import com.russellmorris.presentation.base.BaseViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ShowWeatherViewModel constructor(private val weatherUseCase: WeatherUseCase) :
        BaseViewModel() {

    val weather = MutableLiveData<Resource<Weather>>()
    private val compositeDisposable = CompositeDisposable()

    fun getWeatherData(latitude: String?, longitude: String?, units: String) =
        compositeDisposable.add(weatherUseCase.getWeather(latitude, longitude, BuildConfig.WEATHER_API_KEY, units)
            .doOnSubscribe{ weather.setLoading() }
            .subscribeOn(Schedulers.io())
            .map { it.mapToPresentation() }
            .subscribe(
                { weather.setSuccess(it)},
                { weather.setError(it.message)}
            )
        )

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}