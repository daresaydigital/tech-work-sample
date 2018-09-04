package com.suroid.weatherapp.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.suroid.weatherapp.models.CityWeatherEntity
import io.reactivex.Flowable


@Dao
abstract class CityWeatherDao : BaseDao<CityWeatherEntity> {

    /**
     * Get all CityWeathers along with City and forecast.
     */
    @Query("SELECT * FROM CityWeatherEntity")
    abstract fun getAllCityWeathers(): Flowable<List<CityWeatherEntity>>
}