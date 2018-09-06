package com.suroid.weatherapp.repo

import com.suroid.weatherapp.api.WeatherApi
import com.suroid.weatherapp.db.CityWeatherDao
import com.suroid.weatherapp.models.CityWeatherEntity
import com.suroid.weatherapp.models.remote.ResponseStatus
import com.suroid.weatherapp.util.RxImmediateSchedulerRule
import com.suroid.weatherapp.util.createWeatherResponseModel
import com.suroid.weatherapp.util.mapToWeatherEntityTest
import com.suroid.weatherapp.util.mock
import com.suroid.weatherapp.utils.WEATHER_EXPIRY_THRESHOLD_TIME
import com.suroid.weatherapp.utils.currentTimeInSeconds
import io.reactivex.Observable
import io.reactivex.Observer
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.atLeastOnce
import org.mockito.Mockito.never

@RunWith(JUnit4::class)
class CityWeatherRepositoryTest {
    private val cityWeatherDao = Mockito.mock(CityWeatherDao::class.java)
    private val api: WeatherApi = mock()

    private val repo = CityWeatherRepository(cityWeatherDao, api)

    @Rule
    @JvmField
    val schedulers = RxImmediateSchedulerRule()

    @Test
    fun loadCityWeathersTest() {
        repo.getAllCityWeathers()
        Mockito.verify(cityWeatherDao).getAllCityWeathers()
    }

    @Test
    fun fetchCityWeatherSuccessTest() {
        val id = 123
        val response = createWeatherResponseModel(id)
        Mockito.`when`(api.getWeatherWithId(id)).thenReturn(Observable.just(response))

        val observer = mock<Observer<ResponseStatus<CityWeatherEntity>>>()
        repo.responseSubject.subscribe(observer)

        val cityWeather = response.mapToWeatherEntityTest(currentTimeInSeconds() - WEATHER_EXPIRY_THRESHOLD_TIME)

        repo.fetchWeatherOfCity(cityWeather)
        Mockito.verify(api).getWeatherWithId(id)
        Mockito.verify(observer).onNext(ResponseStatus.loading(true, cityWeather))
        Mockito.verify(observer, atLeastOnce()).onNext(ResponseStatus.loading(false, cityWeather))
        Mockito.verify(cityWeatherDao).update(cityWeather)
        Mockito.verify(observer).onNext(ResponseStatus.success(cityWeather, cityWeather))
        Mockito.verifyNoMoreInteractions(api)
    }

    @Test
    fun fetchCityWeatherFailTest() {
        val id = 123
        val response = createWeatherResponseModel(id)
        Mockito.`when`(api.getWeatherWithId(id)).thenReturn(Observable.just(response))

        val observer = mock<Observer<ResponseStatus<CityWeatherEntity>>>()
        repo.responseSubject.subscribe(observer)

        val cityWeather = response.mapToWeatherEntityTest(currentTimeInSeconds())
        repo.fetchWeatherOfCity(cityWeather)
        Mockito.verify(api, never()).getWeatherWithId(id)
        Mockito.verify(observer).onNext(ResponseStatus.loading(false, cityWeather))
    }

    @Test
    fun fetchCityWeatherLatLongTest() {
        val lat = 123.0
        val long = 456.0
        val response = createWeatherResponseModel()
        Mockito.`when`(api.getWeatherWithLatLong(lat = lat, long = long)).thenReturn(Observable.just(response))

        val observer = mock<Observer<ResponseStatus<CityWeatherEntity>>>()
        repo.responseSubject.subscribe(observer)

        val cityWeather = response.mapToWeatherEntityTest(currentTimeInSeconds())

        repo.fetchWeatherWithLatLong(lat, long, 0)
        Mockito.verify(api).getWeatherWithLatLong(lat, long)
        Mockito.verify(observer).onNext(ResponseStatus.loading(true, 0))
        Mockito.verify(observer, atLeastOnce()).onNext(ResponseStatus.loading(false, 0))
        Mockito.verify(cityWeatherDao).upsert(cityWeather)
        Mockito.verify(observer).onNext(ResponseStatus.success(cityWeather, 0))
        Mockito.verifyNoMoreInteractions(api)
    }
}