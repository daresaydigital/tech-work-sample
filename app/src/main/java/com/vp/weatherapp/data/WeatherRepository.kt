package com.vp.weatherapp.data

import android.util.Log
import com.vp.weatherapp.data.local.dao.WeatherDao
import com.vp.weatherapp.data.local.entity.*
import com.vp.weatherapp.data.mapper.ForecastDataMapper
import com.vp.weatherapp.data.remote.DailyForecastResponse
import com.vp.weatherapp.data.remote.HourlyForecastResponse
import com.vp.weatherapp.data.remote.WeatherApi
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


interface WeatherRepository {
    fun getHourlyForecast(city: String, country: String): Flowable<List<HourlyForecastEntity>>
    fun getDailyForecast(city: String, country: String): Flowable<List<DailyForecastEntity>>
    fun getSelectedCities(): Flowable<List<CityEntity>>
    fun performSearch(search: String): Flowable<List<CityEntity>>
    fun saveSelectedCity(city: CityEntity): Flowable<Boolean>
    fun getSelectedCitiesForecast(): Flowable<List<CityWithForecast>>
    fun deleteSelectedCity(city: CityEntity): Flowable<Boolean>
}

class WeatherRepositoryImpl(private val weatherApi: WeatherApi,
                            private val weatherDao: WeatherDao

) : WeatherRepository {

    // Hourly Forecast

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
                    .map {saveHourlyForecastsToDb(it)
                        it
                    }

    private fun saveHourlyForecastsToDb(forecasts: List<HourlyForecastEntity>) {
        Observable.create { subscriber: ObservableEmitter<Any> ->
            weatherDao.deleteAllHourlyForecasts(forecasts[0].city)
            weatherDao.insertHourlyForecast(forecasts)
            subscriber.onComplete()
        }.subscribeOn(Schedulers.io()).subscribe()
    }

    // Daily Forecast

    override fun getDailyForecast(city: String, country: String): Flowable<List<DailyForecastEntity>> {
        val now = System.currentTimeMillis()
        return Flowable.concatArrayEager(
                getDailyFromDb(city, now),
                getDailyFromApi(city))
    }

    private fun getDailyFromDb(city: String, timestamp: Long): Flowable<List<DailyForecastEntity>> =
            weatherDao.getDailyForecast(city, timestamp)


    private fun getDailyFromApi(city: String): Flowable<List<DailyForecastEntity>> =
            weatherApi.getDailyForecastByCity(city)
                    .materialize()
                    .observeOn(AndroidSchedulers.mainThread())
                    .map {it.error?.let { handleErrorCallback(it) }
                        it
                    }
                    .filter { !it.isOnError }
                    .dematerialize<DailyForecastResponse>()
                    .debounce(400, TimeUnit.MILLISECONDS)
                    .map { ForecastDataMapper().convertRemoteToLocal(it) }
                    // SAVING DATA TO DB
                    .map {saveDailyForecastsToDb(it)
                        it
                    }

    private fun saveDailyForecastsToDb(forecasts: List<DailyForecastEntity>) {
        Observable.create { emitter: ObservableEmitter<Any> ->
            weatherDao.deleteAllDailyForecasts(forecasts[0].city)
            weatherDao.insertDailyForecast(forecasts)
            emitter.onComplete()
        }.subscribeOn(Schedulers.io()).subscribe()
    }

    private fun handleErrorCallback(throwable: Throwable) {
        Log.e("handleErrorCallback", throwable.message)
        throwable.printStackTrace()
    }

    // Selected Cities

    override fun getSelectedCities(): Flowable<List<CityEntity>> {
        return weatherDao.getSelectedCities()
    }

    override fun saveSelectedCity(city: CityEntity): Flowable<Boolean> {
        return getHourlyForecast(city.name, "")
                .concatMap { insertSelectedCity(city) }
    }

    private fun insertSelectedCity(city: CityEntity): Flowable<Boolean> {
        return Flowable.fromCallable {
            weatherDao.insertSelectedCity(SelectedCityEntity(city.cityId))
        }.map { it > 0 }
    }

    override fun deleteSelectedCity(city: CityEntity): Flowable<Boolean> {
        return Flowable.fromCallable {
            weatherDao.deleteSelectedCity(SelectedCityEntity(city.cityId))
        }.map { it > 0 }
    }

    // Search

    override fun performSearch(search: String): Flowable<List<CityEntity>> {
        return weatherDao.performSearch("%$search%")
    }

    //

    override fun getSelectedCitiesForecast(): Flowable<List<CityWithForecast>> {
//        System.currentTimeMillis()
        return weatherDao.getSelectedCitiesForecast()
    }
}