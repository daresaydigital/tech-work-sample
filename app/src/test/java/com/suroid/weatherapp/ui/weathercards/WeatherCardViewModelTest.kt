package com.suroid.weatherapp.ui.weathercards

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.suroid.weatherapp.models.CityWeatherEntity
import com.suroid.weatherapp.models.remote.ResponseStatus
import com.suroid.weatherapp.repo.CityWeatherRepository
import com.suroid.weatherapp.util.RxImmediateSchedulerRule
import com.suroid.weatherapp.util.createCityWeather
import com.suroid.weatherapp.util.mock
import com.suroid.weatherapp.utils.weatherIconForId
import com.suroid.weatherapp.utils.weatherImageForId
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class WeatherCardViewModelTest {
    @Rule
    @JvmField
    val schedulers = RxImmediateSchedulerRule()

    @Rule
    @JvmField
    val architectureComponentsRule = InstantTaskExecutorRule()

    private val cityWeatherRepository = mock<CityWeatherRepository>()

    private lateinit var viewModel: WeatherCardViewModel

    private lateinit var cityWeather: CityWeatherEntity

    @Before
    fun setUp() {
        cityWeather = createCityWeather()
        Mockito.`when`(cityWeatherRepository.responseSubject).thenReturn(PublishSubject.create())
        viewModel = WeatherCardViewModel(cityWeatherRepository)
    }

    @Test
    fun fetchCityWeatherTest() {
        Mockito.doAnswer {
            cityWeatherRepository.responseSubject.onNext(ResponseStatus.loading(true, cityWeather))
            cityWeatherRepository.responseSubject.onNext(ResponseStatus.loading(false, cityWeather))
            cityWeatherRepository.responseSubject.onNext(ResponseStatus.success(cityWeather, cityWeather))
        }.`when`(cityWeatherRepository).fetchWeatherOfCity(cityWeather)


        val observer = mock<Observer<Boolean>>()
        viewModel.loadingStatus.observeForever(observer)
        viewModel.setupWithCity(cityWeather)
        Mockito.verify(observer).onChanged(true)
        Mockito.verify(observer).onChanged(false)

        val cityObserver = mock<Observer<String>>()
        viewModel.city.observeForever(cityObserver)
        Mockito.verify(cityObserver).onChanged("${cityWeather.city.name}, ${cityWeather.city.country}")

        val weatherTitleObserver = mock<Observer<String>>()
        viewModel.weatherTitle.observeForever(weatherTitleObserver)
        Mockito.verify(weatherTitleObserver).onChanged("${cityWeather.currentWeather?.title}")

        val humidityObserver = mock<Observer<String>>()
        viewModel.humidity.observeForever(humidityObserver)
        Mockito.verify(humidityObserver).onChanged("${cityWeather.currentWeather?.humidity}")

        val windObserver = mock<Observer<String>>()
        viewModel.wind.observeForever(windObserver)
        Mockito.verify(windObserver).onChanged("${cityWeather.currentWeather?.windSpeed}")

        val minMaxTempObserver = mock<Observer<String>>()
        viewModel.minMaxTemp.observeForever(minMaxTempObserver)
        Mockito.verify(minMaxTempObserver).onChanged("${cityWeather.currentWeather?.temperature?.minTemp?.toInt()}/${cityWeather.currentWeather?.temperature?.maxTemp?.toInt()}")

        val iconObserver = mock<Observer<Int>>()
        viewModel.icon.observeForever(iconObserver)
        Mockito.verify(iconObserver).onChanged(weatherIconForId(cityWeather.currentWeather?.weather_id ?: 0))

        val imageObserver = mock<Observer<Int>>()
        viewModel.image.observeForever(imageObserver)
        Mockito.verify(imageObserver).onChanged(weatherImageForId(cityWeather.currentWeather?.weather_id ?: 0))
    }


}