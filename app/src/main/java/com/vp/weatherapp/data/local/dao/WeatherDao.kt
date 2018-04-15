package com.vp.weatherapp.data.local.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.vp.weatherapp.data.local.entity.DailyForecastEntity
import com.vp.weatherapp.data.local.entity.HourlyForecastEntity
import io.reactivex.Flowable

import android.arch.persistence.room.OnConflictStrategy.REPLACE


@Dao
interface WeatherDao {

    // Daily
//    @Query("""SELECT * FROM daily_forecast df
//        WHERE df.city = :city AND df.dt > :timestamp
//    """)
//    fun getDailyForecast(city: String, timestamp: Long): Flowable<List<DailyForecastEntity>>
//
//    @Insert(onConflict = REPLACE)
//    fun insertDailyForecast(forecasts: List<DailyForecastEntity>)
//
//    @Query("DELETE FROM daily_forecast")
//    fun deleteAllDailyForecasts()

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
}