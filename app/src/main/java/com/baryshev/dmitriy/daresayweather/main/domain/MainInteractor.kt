package com.baryshev.dmitriy.daresayweather.main.domain

import com.baryshev.dmitriy.daresayweather.R
import com.baryshev.dmitriy.daresayweather.common.data.cache.CacheRepo
import com.baryshev.dmitriy.daresayweather.common.data.exception.WeatherException
import com.baryshev.dmitriy.daresayweather.common.data.geo.GeoRepo
import com.baryshev.dmitriy.daresayweather.common.data.weather.WeatherRepo
import com.baryshev.dmitriy.daresayweather.common.data.weather.pojo.DailyWeather
import com.baryshev.dmitriy.daresayweather.common.data.weather.pojo.ForecastResponse
import com.baryshev.dmitriy.daresayweather.common.data.weather.pojo.WeatherResponse
import com.baryshev.dmitriy.daresayweather.common.domain.IResourceInteractor
import com.baryshev.dmitriy.daresayweather.utils.extensions.*
import io.reactivex.Single
import java.util.*

class MainInteractor(private val cacheRepo: CacheRepo,
                     private val geoRepo: GeoRepo,
                     private val weatherRepo: WeatherRepo,
                     private val resourceInteractor: IResourceInteractor) : IMainInteractor {


    override fun getWeatherByCityName(city: String): Single<MainData.CurrentWeather> {
        return weatherRepo.getCurrentWeatherByCityName(city)
            .flatMap {
                if (it.cod != null && it.cod.toInt() == WeatherRepo.SUCCESS_COD) Single.just(it) else Single.error(
                    WeatherException())
            }
            .doOnSuccess { saveCoordinates(it) }
            .map { response: WeatherResponse -> handleWeatherResponse(response) }
    }

    override fun getCurrentCityWeather(fromCache: Boolean): Single<MainData.CurrentWeather> {
        return initDataSingle(fromCache).flatMap {
            weatherRepo.getCurrentWeatherByCoord(it.first,
                                                 it.second)
        }
            .flatMap {
                if (it.cod != null && it.cod.toInt() == WeatherRepo.SUCCESS_COD) Single.just(it) else Single.error(
                    WeatherException())
            }
            .doOnSuccess{ saveCoordinates(it) }
            .map { response: WeatherResponse -> handleWeatherResponse(response) }
    }

    override fun getCurrentCityForecast(fromCache: Boolean): Single<List<MainData.Forecast>> {
        return initDataSingle(fromCache).flatMap {
            weatherRepo.getForecastWeatherByCoord(it.first,
                                                  it.second)
        }
            .flatMap {
                if (it.cod != null && it.cod.toInt() == WeatherRepo.SUCCESS_COD) Single.just(it) else Single.error(
                    WeatherException())
            }
            .doOnSuccess{ saveCoordinates(it) }
            .flatMap { response: ForecastResponse ->
                response.dailyWeatherData?.let { Single.just(it) } ?: Single.error(
                    WeatherException())
            }
            .map { dailyWeatherData: List<DailyWeather>? ->
                dailyWeatherData?.let {
                    handleForecast(it)
                } ?: Collections.emptyList()
            }
    }

    override fun clear() {
        geoRepo.clearLocationUpdating()
    }

    private fun saveCoordinates(response: ForecastResponse?) {
        response?.city?.coord?.let {
            if (it.lat != null && it.lon != null) cacheRepo.saveLastCoordinates(it.lat.toFloat(),
                                                                                it.lon.toFloat())
        }
    }

    private fun handleForecast(dailyWeatherData: List<DailyWeather>): List<MainData.Forecast> {
        return with(dailyWeatherData) {
            map {
                val weather = it.weather
                val weatherCondition = if (weather != null && !weather.isEmpty()) weather[0] else null
                MainData.Forecast(it.dt.formatToDateString(),
                                  it.dt.formatToDayOfWeek(),
                                  it.temp.min?.temp().orEmpty(),
                                  it.temp.max?.temp().orEmpty(),
                                  getConditionByIcon(weatherCondition?.icon.orEmpty()).icon)
            }
        }
    }

    override fun getForecastByCityName(city: String): Single<List<MainData.Forecast>> {
        return weatherRepo.getForecastWeatherByCityName(city)
            .flatMap {
                if (it.cod != null && it.cod.toInt() == WeatherRepo.SUCCESS_COD) Single.just(it) else Single.error(WeatherException())
            }
            .doOnSuccess{ saveCoordinates(it) }
            .flatMap { response: ForecastResponse ->
                response.dailyWeatherData?.let { Single.just(it) } ?: Single.error(WeatherException())
            }
            .map { dailyWeatherData: List<DailyWeather> -> handleForecast(dailyWeatherData) }
    }

    private fun MainInteractor.handleWeatherResponse(response: WeatherResponse): MainData.CurrentWeather =
        with(response) {
            val weatherCondition = if (weather != null && !weather.isEmpty()) weather[0] else null
            val conditionByIcon = getConditionByIcon(weatherCondition?.icon.orEmpty())
            MainData.CurrentWeather(name.orEmpty { resourceInteractor.getString(R.string.main_tv_city_name_default) },
                                    main?.temp?.temp().orEmpty(),
                                    conditionByIcon.icon,
                                    weatherCondition?.description.orEmpty(),
                                    main?.tempMin?.temp().orEmpty(),
                                    main?.tempMax?.temp().orEmpty(),
                                    wind?.speed?.wind().orEmpty(),
                                    main?.pressure?.mmHg().orEmpty(),
                                    main?.humidity?.humidity().orEmpty(),
                                    resourceInteractor.getString(conditionByIcon.advice))
        }

    private fun initDataSingle(fromCache: Boolean): Single<Pair<Float, Float>> =
        if (fromCache && !cacheRepo.isLastCoordsCacheEmpty()) cacheRepo.getLastCoordinates() else geoRepo.getCurrentCoordinates()

    private fun saveCoordinates(response: WeatherResponse) {
        response.coord?.let {
            if (it.lat != null && it.lon != null) cacheRepo.saveLastCoordinates(it.lat.toFloat(),
                                                                                it.lon.toFloat())

        }
    }

    private fun getConditionByIcon(icon: String): WeatherCondition {
        return when (icon) {
            "01d" -> WeatherCondition.CLEAR_DAY
            "01n" -> WeatherCondition.CLEAR_NIGHT
            "02d" -> WeatherCondition.FEW_CLOUD_DAY
            "02n" -> WeatherCondition.FEW_CLOUD_NIGHT
            "03d" -> WeatherCondition.CLOUD_DAY
            "03n" -> WeatherCondition.CLOUD_NIGHT
            "04d" -> WeatherCondition.BROKEN_CLOUD_DAY
            "04n" -> WeatherCondition.BROKEN_CLOUD_NIGHT
            "09d" -> WeatherCondition.SHOWER_RAIN_DAY
            "09n" -> WeatherCondition.SHOWER_RAIN_NIGHT
            "10d" -> WeatherCondition.RAIN_DAY
            "10n" -> WeatherCondition.RAIN_NIGHT
            "11d" -> WeatherCondition.THUNDER_DAY
            "11n" -> WeatherCondition.THUNDER_NIGHT
            "13d" -> WeatherCondition.SNOW_DAY
            "13n" -> WeatherCondition.SNOW_NIGHT
            "50d" -> WeatherCondition.FOG_DAY
            "50n" -> WeatherCondition.FOG_NIGHT
            else -> WeatherCondition.UNKNOWN
        }
    }
}
