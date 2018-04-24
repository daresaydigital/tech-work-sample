package com.vp.weatherapp.data.local.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.IGNORE
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.vp.weatherapp.data.local.entity.*
import io.reactivex.Flowable
import io.reactivex.Single


@Dao
interface WeatherDao {
    // TODO: optimize tables and queries
    // TODO: handle timezones
    // TODO:

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

    @Query("""SELECT c.city_id, c.name, c.country, c.lat, c.lon,
        hf.`temp`, hf.description, hf.icon, min(hf.dt)
        FROM selected_city sc
        LEFT JOIN city c
        ON sc.city_id = c.city_id
        LEFT JOIN hourly_forecast hf
        ON hf.city_id = c.city_id
        GROUP BY c.city_id
        ORDER BY c.name
        """)
//    WHERE hf.dt > :timestamp
    fun getSelectedCitiesForecast(): Single<List<CityWithForecast>>

    // Current Forecast

    @Query("""SELECT c.city_id, c.name, c.country, c.lat, c.lon,
        hf.`temp`, hf.description, hf.icon, min(hf.dt)
        FROM hourly_forecast hf
        LEFT JOIN city c
            ON hf.city_id = c.city_id
            AND hf.city_id = :cityId
        GROUP BY c.city_id
        ORDER BY c.name
        """)
//    WHERE hf.dt > :timestamp
    fun getCityCurrentWeather(cityId: Long): Flowable<List<CityWithForecast>>


    // City Daily Forecast

    @Query("""SELECT df.dt, df.icon, df.temp_day, df.temp_night
        FROM daily_forecast df
        WHERE df.city_id = :cityId AND df.dt > :timestamp
        """)
    fun getCityDailyForecast(cityId: Long, timestamp: Long): Flowable<List<CityDailyForecast>>

    @Query("""SELECT *
        FROM daily_forecast df
        WHERE df.city_id = :cityId
        AND df.dt > :timestamp
        """)
    fun getDailyForecast(cityId: Long, timestamp: Long): Flowable<List<DailyForecastEntity>>

    @Insert(onConflict = REPLACE)
    fun insertDailyForecasts(forecasts: List<DailyForecastEntity>)

    @Query("DELETE FROM daily_forecast WHERE city_id = :cityId")
    fun deleteAllDailyForecasts(cityId: Long)


    // Daily
//    @Query("""SELECT *
//        FROM daily_forecast df
//        WHERE df.city = :city AND df.dt > :timestamp""")
//    fun getDailyForecast(city: String, timestamp: Long): Flowable<List<DailyForecastEntity>>


    // City Hourly Forecast

    @Query("""SELECT hf.dt, hf.icon, hf.`temp`
        FROM hourly_forecast hf
        WHERE hf.city_id = :cityId AND hf.dt > :timestamp
        """)
    fun getCityHourlyForecast(cityId: Long, timestamp: Long): Flowable<List<CityHourlyForecast>>

    @Query("""SELECT *
        FROM hourly_forecast hf
        WHERE hf.city_id = :cityId
        AND hf.dt > :timestamp
        """)
    fun getHourlyForecast(cityId: Long, timestamp: Long): Flowable<List<HourlyForecastEntity>>

    @Insert(onConflict = REPLACE)
    fun insertHourlyForecasts(forecasts: List<HourlyForecastEntity>)

    @Query("DELETE FROM hourly_forecast WHERE city_id = :cityId")
    fun deleteAllHourlyForecasts(cityId: Long)

    // Hourly
//    @Query("""SELECT *
//        FROM hourly_forecast hf
//        WHERE hf.city_id = :cityId AND hf.dt > :timestamp
//        GROUP BY hf.city
//        HAVING COUNT(*) > 8
//        ORDER BY hf.dt""")
//    fun getHourlyForecast(cityId: Long, timestamp: Long): Flowable<List<HourlyForecastEntity>>
}