package com.vp.weatherapp.data

import com.vp.weatherapp.data.local.dao.WeatherDao
import com.vp.weatherapp.data.local.entity.*
import com.vp.weatherapp.data.mapper.convertDaily
import com.vp.weatherapp.data.mapper.convertHourly
import com.vp.weatherapp.data.remote.DailyForecastResponse
import com.vp.weatherapp.data.remote.HourlyForecastResponse
import com.vp.weatherapp.data.remote.WeatherApi
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.*


//import java.util.function.Function


interface WeatherRepository {
    fun getHourlyForecastById(cityId: Long): Flowable<List<HourlyForecastEntity>>
    fun getHourlyFromApi(cityId: Long): Single<HourlyForecastResponse>
    fun getDailyForecastById(cityId: Long): Flowable<List<DailyForecastEntity>>
    fun getDailyFromApi(cityId: Long): Single<DailyForecastResponse>
    fun getSelectedCities(): Flowable<List<CityEntity>>
    fun performSearch(search: String): Flowable<List<CityEntity>>
    fun saveSelectedCity(city: CityEntity): Single<Boolean>
    fun getSelectedCitiesForecast(): Single<List<CityWithForecast>>
    fun deleteSelectedCity(city: CityEntity): Flowable<Boolean>
}

class WeatherRepositoryImpl(private val weatherApi: WeatherApi,
                            private val weatherDao: WeatherDao

) : WeatherRepository {

    // Current Weather

    // Hourly Forecast

    override fun getHourlyForecastById(cityId: Long): Flowable<List<HourlyForecastEntity>> {
        return getHourlyFromDb(cityId)
    }

    private fun getHourlyFromDb(cityId: Long): Flowable<List<HourlyForecastEntity>> {
        println("getting hourly from DB for: $cityId")
        val timestamp = getStartOfCurrentHour()
        return weatherDao.getHourlyForecast(cityId, timestamp)
    }

    override fun getHourlyFromApi(cityId: Long): Single<HourlyForecastResponse> {
        println("getting hourly from API for: $cityId")
        return weatherApi.getHourlyForecastByCityId(cityId)
                .doOnSuccess {
                    println("inserting hourly forecasts")
                    val entities = convertHourly(it)
                    weatherDao.deleteAllHourlyForecasts(cityId)
                    weatherDao.insertHourlyForecasts(entities)
                }
    }

    private fun getStartOfCurrentHour(): Long {
        val date = GregorianCalendar()
        date.set(Calendar.MINUTE, 0)
        date.set(Calendar.SECOND, 0)
        date.set(Calendar.MILLISECOND, 0)
        return date.time.time / 1000
    }

    // Daily Forecast

    override fun getDailyForecastById(cityId: Long): Flowable<List<DailyForecastEntity>> {
        return getDailyFromDb(cityId)
    }

    private fun getDailyFromDb(cityId: Long): Flowable<List<DailyForecastEntity>> {
        println("getting daily from DB for: $cityId")
        val timestamp = getStartOfToday()
        return weatherDao.getDailyForecast(cityId, timestamp)
    }

    override fun getDailyFromApi(cityId: Long): Single<DailyForecastResponse> {
        println("getting daily from API for: $cityId")
        return weatherApi.getDailyForecastByCityId(cityId)
                .doOnSuccess {
                    println("inserting daily forecasts")
                    val entities = convertDaily(it)
                    weatherDao.deleteAllDailyForecasts(cityId)
                    weatherDao.insertDailyForecasts(entities)
                }
    }

    private fun getStartOfToday(): Long {
        val date = GregorianCalendar()
        date.set(Calendar.HOUR_OF_DAY, 0)
        date.set(Calendar.MINUTE, 0)
        date.set(Calendar.SECOND, 0)
        date.set(Calendar.MILLISECOND, 0)
        return date.time.time / 1000
    }

    // Selected Cities

    override fun getSelectedCities(): Flowable<List<CityEntity>> {
        return weatherDao.getSelectedCities()
    }

    override fun saveSelectedCity(city: CityEntity): Single<Boolean> {
        return insertSelectedCity(city)
                .doOnSuccess { getHourlyFromApi(city.cityId) }
    }

    private fun insertSelectedCity(city: CityEntity): Single<Boolean> {
        return Single.fromCallable {
            weatherDao.insertSelectedCity(SelectedCityEntity(city.cityId))
        }.map { it > 0 }
    }

    override fun getSelectedCitiesForecast(): Single<List<CityWithForecast>> {
        return weatherDao.getSelectedCitiesForecast()
    }

    override fun deleteSelectedCity(city: CityEntity): Flowable<Boolean> {
        return Flowable.fromCallable {
            weatherDao.deleteSelectedCity(SelectedCityEntity(city.cityId))
        }.map { it > 0 }
    }

    // Search

    override fun performSearch(search: String): Flowable<List<CityEntity>> {
        return weatherDao.performSearch("$search%")
    }

}