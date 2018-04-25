package com.vp.weatherapp.ui.main

import android.annotation.SuppressLint
import com.vp.weatherapp.data.WeatherRepository
import com.vp.weatherapp.data.local.entity.DailyForecastEntity
import com.vp.weatherapp.data.local.entity.HourlyForecastEntity
import com.vp.weatherapp.data.remote.Envelope
import com.vp.weatherapp.ui.AbstractPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class WeatherPresenter(private val weatherRepository: WeatherRepository,
                       override var view: WeatherContract.View)
    : AbstractPresenter<WeatherContract.View, WeatherContract.Presenter>()
        , WeatherContract.Presenter {

    @SuppressLint("RxLeakedSubscription")
    override fun getHourlyForecast(cityId: Long) {
        launch {
            weatherRepository.getHourlyForecastById(cityId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { hourlyForecasts ->
                                handleHourlyForecasts(hourlyForecasts)
                            },
                            { err ->
                                view.onError("Error", err.message ?: "Something bad happened")
                            }
                    )
        }
    }

    private fun handleHourlyForecasts(hourlyForecasts: List<HourlyForecastEntity>) {
        if (hourlyForecasts.isNotEmpty()) {
            view.displayCurrentWeather(hourlyForecasts[0])
            view.displayHourlyForecast(hourlyForecasts)
        } else {
            view.showNoHourlyForecast()
        }
    }

    @SuppressLint("RxLeakedSubscription")
    override fun updateHourlyForecast(cityId: Long) {
        launch {
            weatherRepository.getHourlyFromApi(cityId)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({}, { err -> handleApiError(err) })
        }
    }

    @SuppressLint("RxLeakedSubscription")
    override fun getDailyForecast(cityId: Long) {
        launch {
            weatherRepository.getDailyForecastById(cityId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { dailyForecasts->
                                handleDailyForecasts(dailyForecasts) },
                            { err ->
                                view.onError("Error title", "error message: ${err.message}")
                            }
                    )
        }
    }

    private fun handleDailyForecasts(dailyForecasts: List<DailyForecastEntity>) {
        if (dailyForecasts.isNotEmpty()) {
            view.displayDailyForecast(dailyForecasts)
        } else {
            view.showNoDailyForecast()
        }
    }

    @SuppressLint("RxLeakedSubscription")
    override fun updateDailyForecast(cityId: Long) {
        launch {
            weatherRepository.getDailyFromApi(cityId)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({}, {err -> handleApiError(err) })
        }
    }

    private fun handleApiError(err: Throwable) {
        when(err) {
            is Envelope.FailureException -> view.onApiError(err.message ?: "Generic error message")
            else -> view.onError("Error title", "error message: ${err.message}")
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