package com.vp.weatherapp.ui.main

import android.annotation.SuppressLint
import com.vp.weatherapp.data.WeatherRepository
import com.vp.weatherapp.ui.AbstractPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class WeatherPresenter(private val weatherRepository: WeatherRepository,
                       override var view: WeatherContract.View)
    : AbstractPresenter<WeatherContract.View, WeatherContract.Presenter>(),
        WeatherContract.Presenter {

    @SuppressLint("RxLeakedSubscription")
    override fun getHourlyForecast(cityId: Long) {
        launch {
            weatherRepository.getHourlyForecastById(cityId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { data ->
                                view.displayHourlyForecast(data) },
                            { err ->
                                println(err)
                            }
                    )
        }
    }

    @SuppressLint("RxLeakedSubscription")
    override fun updateHourlyForecast(cityId: Long) {
        launch {
            weatherRepository.getHourlyFromApi(cityId)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({}, {err ->
                        println(err)
                        err.printStackTrace()
                    })
        }
    }

    @SuppressLint("RxLeakedSubscription")
    override fun getDailyForecast(cityId: Long) {
        launch {
            weatherRepository.getDailyForecastById(cityId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { data->
                                view.displayDailyForecast(data) },
                            { err ->
                                println(err)
                            }
                    )
        }
    }

    @SuppressLint("RxLeakedSubscription")
    override fun updateDailyForecast(cityId: Long) {
        launch {
            weatherRepository.getDailyFromApi(cityId)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({}, {err ->
                        println(err)
                        err.printStackTrace()
                    })
        }
    }

    override fun getCurrentWeather(cityId: Long) {
//        launch {
//            weatherRepository.getDailyForecast(cityId)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(
//                            { resource ->
//                                view.displayDailyForecast(resource.data!!) },
//                            { err -> {} }
//                    )
//        }
    }

}