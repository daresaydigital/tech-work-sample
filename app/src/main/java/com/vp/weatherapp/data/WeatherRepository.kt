package com.vp.weatherapp.data

import android.util.Log
import com.vp.weatherapp.data.local.dao.WeatherDao
import com.vp.weatherapp.data.local.entity.HourlyForecastEntity
import com.vp.weatherapp.data.mapper.ForecastDataMapper
import com.vp.weatherapp.data.remote.HourlyForecastResponse
import com.vp.weatherapp.data.remote.WeatherApi
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


interface WeatherRepository {
    fun getHourlyForecast(city: String, country: String): Flowable<List<HourlyForecastEntity>>
}

class WeatherRepositoryImpl(private val weatherApi: WeatherApi,
                            private val weatherDao: WeatherDao

) : WeatherRepository {

    override fun getHourlyForecast(city: String, country: String): Flowable<List<HourlyForecastEntity>> {
        val now = System.currentTimeMillis()
        return Flowable.concatArrayEager(
                getHourlyFromDb(city, now),
                getHourlyFromApi(city))
    }

    private fun getHourlyFromDb(city: String, timestamp: Long): Flowable<List<HourlyForecastEntity>> =
            weatherDao.getHourlyForecast(city, timestamp)


    private fun getHourlyFromApi(city: String): Flowable<List<HourlyForecastEntity>> =
            weatherApi.getHourlyForecastByCity(city)
                    .materialize()
                    .observeOn(AndroidSchedulers.mainThread())
                    .map {it.error?.let { handleErrorCallback(it) }
                        it
                    }
                    .filter { !it.isOnError }
                    .dematerialize<HourlyForecastResponse>()
                    .debounce(400, TimeUnit.MILLISECONDS)
                    .map { ForecastDataMapper().convertRemoteToLocal(it) }
                    // SAVING DATA TO DB
                    .map { forecasts : List<HourlyForecastEntity> ->
                        Observable.create { subscriber: ObservableEmitter<Any> ->
                            weatherDao.deleteAllHourlyForecasts(forecasts[0].city)
                            weatherDao.insertHourlyForecast(forecasts)
                            subscriber.onComplete()
                        }.subscribeOn(Schedulers.io()).subscribe()
                        forecasts
                    }

    private fun handleErrorCallback(throwable: Throwable) {
        Log.e("handleErrorCallback", throwable.message)
        throwable.printStackTrace()
    }
}