package com.suroid.weatherapp.repo

import com.suroid.weatherapp.db.CityDao
import com.suroid.weatherapp.db.CityWeatherDao
import com.suroid.weatherapp.models.CityWeatherEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

/**
 * CityWeatherRepository handles all the data related task, it can act as a mediator between data accessor and data provider modules
 * @param cityWeatherDao [CityWeatherDao] instance to load data from
 */
class CityWeatherRepository(private val cityWeatherDao: CityWeatherDao) {

    /**
     * Save a new cityWeather to the database. This method performs async operation
     */
    fun saveCityWeather(cityWeather: CityWeatherEntity) {
        Completable.fromCallable {
            cityWeatherDao.insert(cityWeather)
        }.subscribeOn(Schedulers.io()).subscribe()
    }

    /**
     * Load All city weathers from database
     */
    fun getAllCityWeathers(): Flowable<List<CityWeatherEntity>> {
        return cityWeatherDao.getAllCityWeathers()
    }
}