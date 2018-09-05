package com.suroid.weatherapp.repo

import com.suroid.weatherapp.api.WeatherApi
import com.suroid.weatherapp.db.CityWeatherDao
import com.suroid.weatherapp.models.CityWeatherEntity
import com.suroid.weatherapp.models.remote.ResponseStatus
import com.suroid.weatherapp.utils.*
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

/**
 * CityWeatherRepository handles all the data related task, it can act as a mediator between data accessor and data provider modules
 * @param cityWeatherDao [CityWeatherDao] instance to load local data from
 * @param weatherApi [WeatherApi] instance to load remote data from
 */
class CityWeatherRepository(private val cityWeatherDao: CityWeatherDao, private val weatherApi: WeatherApi) {
    val responseSubject = PublishSubject.create<ResponseStatus<CityWeatherEntity>>()

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
    fun getAllCityWeathers(): Single<List<CityWeatherEntity>> {
        return cityWeatherDao.getAllCityWeathers()
    }

    fun fetchWeatherOfCity(cityWeather: CityWeatherEntity) {
        if (currentTimeInSeconds() - cityWeather.date < WEATHER_EXPIRY_THRESHOLD_TIME) {
            responseSubject.loading(false, cityWeather)
            return
        }
        weatherApi.getWeather(cityWeather.city.name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { responseSubject.loading(true, cityWeather) }
                .subscribe(
                        { result ->
                            val cityWeatherEntity = result.mapToWeatherEntity(cityWeather)
                            Single.fromCallable {
                                cityWeatherDao.update(cityWeatherEntity)
                            }.subscribeOn(Schedulers.io()).subscribe()
                            responseSubject.success(cityWeatherEntity, cityWeather)
                        },
                        { error -> responseSubject.failed(error, cityWeather) }
                )
    }
}