package com.suroid.weatherapp.repo

import com.suroid.weatherapp.db.CityDao
import com.suroid.weatherapp.models.City
import io.reactivex.Single
import javax.inject.Singleton

/**
 * CityRepository handles all the data related task, it can act as a mediator between data accessor and data provider modules
 * @param cityDao [CityDao] instance to load data from
 */
@Singleton
class CityRepository(private val cityDao: CityDao) {

    /**
     * Load All cities from database
     */
    fun getAllCities(): Single<List<City>> {
        return cityDao.getAllCities()
    }
}