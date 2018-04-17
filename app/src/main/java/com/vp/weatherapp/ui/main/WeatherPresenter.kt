package com.vp.weatherapp.ui.main

import com.vp.weatherapp.data.WeatherRepository
import com.vp.weatherapp.ui.AbstractPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class WeatherPresenter(private val weatherRepository: WeatherRepository,
                       override var view: WeatherContract.View)
    : AbstractPresenter<WeatherContract.View, WeatherContract.Presenter>(),
        WeatherContract.Presenter {

    override fun getHourlyForecast(city: String, country: String) {
        launch {
            weatherRepository.getHourlyForecast(city, country)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { view::displayHourlyForecast },
                            { err -> {} }
                    )
        }
    }

    override fun getDailyForecast(city: String, country: String) {
        launch {
            weatherRepository.getDailyForecast(city, country)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { view::displayDailyForecast },
                            { err -> {} }
                    )
        }
    }

}