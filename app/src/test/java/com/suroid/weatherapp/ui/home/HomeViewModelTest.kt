package com.suroid.weatherapp.ui.home

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import android.location.Location
import com.suroid.weatherapp.models.City
import com.suroid.weatherapp.models.CityWeatherEntity
import com.suroid.weatherapp.models.WeatherModel
import com.suroid.weatherapp.models.remote.ResponseStatus
import com.suroid.weatherapp.repo.CityWeatherRepository
import com.suroid.weatherapp.util.RxImmediateSchedulerRule
import com.suroid.weatherapp.util.createCityWeather
import com.suroid.weatherapp.util.mock
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.never

@RunWith(JUnit4::class)
class HomeViewModelTest {
    @Rule
    @JvmField
    val schedulers = RxImmediateSchedulerRule()

    @Rule
    @JvmField
    val architectureComponentsRule = InstantTaskExecutorRule()

    private val cityWeatherRepository = mock<CityWeatherRepository>()

    private lateinit var viewModel: HomeViewModel

    private lateinit var cityWeatherList: ArrayList<CityWeatherEntity>

    private val observer = mock<Observer<ArrayList<CityWeatherEntity>>>()

    private val responseSubject = PublishSubject.create<ResponseStatus<CityWeatherEntity>>()

    @Before
    fun setUp() {
        cityWeatherList = ArrayList<CityWeatherEntity>().apply {
            add(createCityWeather())
        }
        Mockito.`when`(cityWeatherRepository.getAllCityWeathers()).thenReturn(Single.just(cityWeatherList))
        Mockito.`when`(cityWeatherRepository.responseSubject).thenReturn(responseSubject)
        viewModel = HomeViewModel(cityWeatherRepository)

        viewModel.cityWeatherListLiveData.observeForever(observer)
        Mockito.verify(observer).onChanged(cityWeatherList)
        Mockito.reset(observer)
    }

    @Test
    fun saveNewCityTest() {
        val city = City("abcd", "efgh", 1246)
        val cityWeatherEntity = CityWeatherEntity(city.id, WeatherModel(), city = city)
        cityWeatherList.add(cityWeatherEntity)
        viewModel.saveNewCity(city)

        Mockito.verify(cityWeatherRepository).saveCityWeather(cityWeather = cityWeatherEntity)
        Mockito.verify(observer).onChanged(cityWeatherList)
    }

    @Test
    fun saveNewFailTest() {
        val city = City("abcd", "efgh", 0)
        val cityWeatherEntity = CityWeatherEntity(city.id, WeatherModel(), city = city)
        // Add city to existing list
        cityWeatherList.add(cityWeatherEntity)
        viewModel.cityWeatherListLiveData.value?.add(cityWeatherEntity)
        viewModel.saveNewCity(city)

        Mockito.verify(cityWeatherRepository, never()).saveCityWeather(cityWeather = cityWeatherEntity)
        Mockito.verify(observer, never()).onChanged(cityWeatherList)
    }

    @Test
    fun fetchWeatherWithLocationTest() {
        val location = mock<Location>()
        `when`(location.latitude).thenReturn(28.530326302293503)
        `when`(location.longitude).thenReturn(77.21394667401911)
        viewModel.fetchForCurrentLocation(location)
        Mockito.verify(cityWeatherRepository).fetchWeatherWithLatLong(location.latitude, location.longitude, HomeViewModel.UNKNOWN_CITY)
    }

    @Test
    fun weatherWithLocationFetchedTest() {
        val city = City("abcd", "efgh", 0)
        val cityWeatherEntity = CityWeatherEntity(city.id, WeatherModel(), city = city)
        responseSubject.onNext(ResponseStatus.success(cityWeatherEntity, HomeViewModel.UNKNOWN_CITY))
        cityWeatherList.add(cityWeatherEntity)
        Mockito.verify(observer).onChanged(cityWeatherList)
    }

    @Test
    fun weatherWithLocationFetchFailTest() {
        responseSubject.onNext(ResponseStatus.failure(Throwable(), HomeViewModel.UNKNOWN_CITY))
        val observer = mock<Observer<Boolean>>()
        viewModel.fetchCityResult.observeForever(observer)
        Mockito.verify(observer).onChanged(false)
    }

}