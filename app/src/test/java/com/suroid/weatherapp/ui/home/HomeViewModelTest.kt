package com.suroid.weatherapp.ui.home

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.suroid.weatherapp.models.City
import com.suroid.weatherapp.models.CityWeatherEntity
import com.suroid.weatherapp.models.WeatherModel
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

    @Before
    fun setUp() {
        cityWeatherList = ArrayList<CityWeatherEntity>().apply {
            add(createCityWeather())
        }
        Mockito.`when`(cityWeatherRepository.getAllCityWeathers()).thenReturn(Single.just(cityWeatherList))
        Mockito.`when`(cityWeatherRepository.responseSubject).thenReturn(PublishSubject.create())
        viewModel = HomeViewModel(cityWeatherRepository)
    }

    @Test
    fun fetchCityWeatherTest() {
        val observer = mock<Observer<ArrayList<CityWeatherEntity>>>()
        viewModel.cityWeatherListLiveData.observeForever(observer)
        Mockito.verify(observer).onChanged(cityWeatherList)
    }

    @Test
    fun saveNewCityTest() {
        val observer = mock<Observer<ArrayList<CityWeatherEntity>>>()
        val city = City("abcd","efgh", 1246)
        val cityWeatherEntity = CityWeatherEntity(city.id, WeatherModel(), city = city)
        cityWeatherList.add(cityWeatherEntity)
        viewModel.saveNewCity(city)
        Mockito.verify(cityWeatherRepository).saveCityWeather(cityWeather = cityWeatherEntity)
        viewModel.cityWeatherListLiveData.observeForever(observer)
        Mockito.verify(observer).onChanged(cityWeatherList)
    }

    @Test
    fun saveNewFailTest() {
        val observer = mock<Observer<ArrayList<CityWeatherEntity>>>()
        val city = City("abcd","efgh", 0)
        val cityWeatherEntity = CityWeatherEntity(city.id, WeatherModel(), city = city)
        viewModel.saveNewCity(city)
        Mockito.verify(cityWeatherRepository).saveCityWeather(cityWeather = cityWeatherEntity)
        viewModel.cityWeatherListLiveData.observeForever(observer)
        Mockito.verify(observer, never()).onChanged(cityWeatherList)
    }

}