package com.vp.weatherapp.data.local.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.IGNORE
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.vp.weatherapp.data.local.entity.*
import io.reactivex.Flowable


@Dao
interface WeatherDao {
    //TODO: optimize tables and queries

    // Hourly

    @Query("""SELECT *
        FROM hourly_forecast hf
        WHERE hf.city = :city AND hf.dt > :timestamp
        GROUP BY hf.city
        HAVING COUNT(*) > 8
        ORDER BY hf.dt""")
    fun getHourlyForecast(city: String, timestamp: Long): Flowable<List<HourlyForecastEntity>>

    @Insert(onConflict = REPLACE)
    fun insertHourlyForecast(forecasts: List<HourlyForecastEntity>)

    @Query("DELETE FROM hourly_forecast WHERE city = :city")
    fun deleteAllHourlyForecasts(city: String)


    // Daily

    @Query("""SELECT *
        FROM daily_forecast df
        WHERE df.city = :city AND df.dt > :timestamp""")
    fun getDailyForecast(city: String, timestamp: Long): Flowable<List<DailyForecastEntity>>

    @Insert(onConflict = REPLACE)
    fun insertDailyForecast(forecasts: List<DailyForecastEntity>)

    @Query("DELETE FROM daily_forecast WHERE city = :city")
    fun deleteAllDailyForecasts(city: String)


    // Search Cities

    @Query("SELECT * FROM city WHERE name LIKE :search ORDER BY name LIMIT 100")
    fun performSearch(search: String): Flowable<List<CityEntity>>


    // Selected cities

    @Query("""SELECT c.city_id, c.name, c.country, c.lat, c.lon
        FROM selected_city sc
        LEFT JOIN city c
        ON c.city_id = sc.city_id
        """)
    fun getSelectedCities(): Flowable<List<CityEntity>>

    @Insert(onConflict = IGNORE)
    fun insertSelectedCity(city: SelectedCityEntity): Long

    @Delete
    fun deleteSelectedCity(city: SelectedCityEntity): Int


    // Selected Cities with forecast

//    @Transaction
//    @Query("SELECT * FROM selected_city")
//    fun getSelectedCitiesWithForecast(): Flowable<List<CityWithForecast>>

    // TODO: use city_id instead of name in hf table
    @Query("""SELECT c.city_id, c.name, c.country, c.lat, c.lon, hf.`temp`, min(hf.dt)
        FROM selected_city sc
        LEFT JOIN city c
        ON c.city_id = sc.city_id
        LEFT JOIN hourly_forecast hf
        ON hf.city = c.name
        GROUP BY c.city_id
        ORDER BY c.name
        """)
//    WHERE hf.dt > :timestamp
    fun getSelectedCitiesForecast(): Flowable<List<CityWithForecast>>
}