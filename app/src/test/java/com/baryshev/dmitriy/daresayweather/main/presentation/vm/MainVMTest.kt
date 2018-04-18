package com.baryshev.dmitriy.daresayweather.main.presentation.vm

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.baryshev.dmitriy.daresayweather.TestRxScheduler
import com.baryshev.dmitriy.daresayweather.common.data.exception.WeatherException
import com.baryshev.dmitriy.daresayweather.common.domain.IResourceInteractor
import com.baryshev.dmitriy.daresayweather.main.domain.IMainInteractor
import com.baryshev.dmitriy.daresayweather.main.domain.MainData
import com.baryshev.dmitriy.daresayweather.main.presentation.view.CurrentWeatherViewResult
import com.baryshev.dmitriy.daresayweather.main.presentation.view.ForecastViewResult
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

/**
 * 4/17/2018.
 */
class MainVMTest {
    @Mock
    private lateinit var forecastObserver: Observer<ForecastViewResult>
    @Mock
    private lateinit var weatherObserver: Observer<CurrentWeatherViewResult>
    @Mock
    private lateinit var searchStateObserver: Observer<Boolean>
    @Mock
    private lateinit var mainInteractor: IMainInteractor
    @Mock
    private lateinit var resourceInteractor: IResourceInteractor
    private var viewModel: MainVM? = null

    //    the hack field to test LiveData
    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = MainVM(TestRxScheduler(), mainInteractor, resourceInteractor)
        viewModel?.forecastData?.observeForever(forecastObserver)
        viewModel?.currentWeatherData?.observeForever(weatherObserver)
        viewModel?.searchStateData?.observeForever(searchStateObserver)
    }

    private fun initCurrentWeatherData() = MainData.CurrentWeather("test",
                                                                   "test",
                                                                   1,
                                                                   "test",
                                                                   "test",
                                                                   "test",
                                                                   "test",
                                                                   "test",
                                                                   "test",
                                                                   "test")

    private fun initForecastData() = MainData.Forecast("test",
                                                       "test",
                                                       "test",
                                                       "test",
                                                       1)

    @Test
    fun `load current weather data with success`() {

        val weather = initCurrentWeatherData()
        val forecast = initForecastData()
        val forecasts = listOf(forecast, forecast)

       `when`(mainInteractor.getCurrentCityWeather(anyBoolean())).thenReturn(Single.just(weather))
       `when`(mainInteractor.getCurrentCityForecast(anyBoolean())).thenReturn(Single.just(forecasts))

        viewModel?.searchStateData?.value = false
        viewModel?.loadData(true)

        verify(weatherObserver, never()).onChanged(Mockito.any(CurrentWeatherViewResult.Error::class.java))
        verify(forecastObserver, never()).onChanged(Mockito.any(ForecastViewResult.Error::class.java))
        verify(weatherObserver).onChanged(CurrentWeatherViewResult.Success(weather))
        verify(forecastObserver).onChanged(ForecastViewResult.Success(forecasts))
    }

    @Test
    fun `load current weather data with error`() {

        val weatherException = WeatherException()
        `when`(mainInteractor.getCurrentCityWeather(anyBoolean())).thenReturn(Single.error(weatherException))
        `when`(mainInteractor.getCurrentCityForecast(anyBoolean())).thenReturn(Single.error(weatherException))
        `when`(resourceInteractor.getString(anyInt())).thenReturn("Error")
        viewModel?.searchStateData?.value = false
        viewModel?.loadData(true)

        verify(weatherObserver).onChanged(CurrentWeatherViewResult.Error("Error", weatherException))
        verify(forecastObserver).onChanged(ForecastViewResult.Error("Error", weatherException))
        verify(weatherObserver,
               never()).onChanged(Mockito.any(CurrentWeatherViewResult.Success::class.java))
        verify(forecastObserver,
               never()).onChanged(Mockito.any(ForecastViewResult.Success::class.java))
    }

    @Test
    fun `force load current weather with success`() {

        val weather = initCurrentWeatherData()
        val forecast = initForecastData()
        val forecasts = listOf(forecast, forecast)

        `when`(mainInteractor.getCurrentCityWeather(anyBoolean())).thenReturn(Single.just(weather))
        `when`(mainInteractor.getCurrentCityForecast(anyBoolean())).thenReturn(Single.just(forecasts))

        viewModel?.searchStateData?.value = false
        viewModel?.loadData(true, forceLoading = true)

        verify(weatherObserver, never()).onChanged(Mockito.any(CurrentWeatherViewResult.Error::class.java))
        verify(forecastObserver, never()).onChanged(Mockito.any(ForecastViewResult.Error::class.java))
        verify(weatherObserver).onChanged(CurrentWeatherViewResult.Success(weather))
        verify(forecastObserver).onChanged(ForecastViewResult.Success(forecasts))
    }

    @Test
    fun `no force load current weather but empty data provide success loading`() {

        val weather = initCurrentWeatherData()
        val forecast = initForecastData()
        val forecasts = listOf(forecast, forecast)

        `when`(mainInteractor.getCurrentCityWeather(anyBoolean())).thenReturn(Single.just(weather))
        `when`(mainInteractor.getCurrentCityForecast(anyBoolean())).thenReturn(Single.just(forecasts))

        viewModel?.searchStateData?.value = false
        viewModel?.loadData(true, forceLoading = false)

        verify(weatherObserver, never()).onChanged(Mockito.any(CurrentWeatherViewResult.Error::class.java))
        verify(forecastObserver, never()).onChanged(Mockito.any(ForecastViewResult.Error::class.java))
        verify(weatherObserver).onChanged(CurrentWeatherViewResult.Success(weather))
        verify(forecastObserver).onChanged(ForecastViewResult.Success(forecasts))
    }

    @Test
    fun `no force load current weather and not empty data provide nothing`() {

        val weather = initCurrentWeatherData()
        val forecast = initForecastData()
        val forecasts = listOf(forecast, forecast)

        `when`(mainInteractor.getCurrentCityWeather(anyBoolean())).thenReturn(Single.just(weather))
        `when`(mainInteractor.getCurrentCityForecast(anyBoolean())).thenReturn(Single.just(forecasts))

        viewModel?.currentWeatherData?.value = CurrentWeatherViewResult.Progress
        viewModel?.forecastData?.value = ForecastViewResult.Progress
        viewModel?.searchStateData?.value = false
        viewModel?.loadData(true, forceLoading = false)

        verify(weatherObserver, never()).onChanged(Mockito.any(CurrentWeatherViewResult.Error::class.java))
        verify(forecastObserver, never()).onChanged(Mockito.any(ForecastViewResult.Error::class.java))
        verify(weatherObserver, never()).onChanged(Mockito.any(CurrentWeatherViewResult.Success::class.java))
        verify(forecastObserver, never()).onChanged(Mockito.any(ForecastViewResult.Success::class.java))
    }

    @Test
    fun `load weather data by city name with success`() {

        val weather = initCurrentWeatherData()
        val forecast = initForecastData()
        val forecasts = listOf(forecast, forecast)

       `when`(mainInteractor.getWeatherByCityName(anyString())).thenReturn(Single.just(weather))
       `when`(mainInteractor.getForecastByCityName(anyString())).thenReturn(Single.just(forecasts))

        viewModel?.searchStateData?.value = true
        viewModel?.searchText = "Test"
        viewModel?.loadData()

        verify(weatherObserver,
               never()).onChanged(Mockito.any(CurrentWeatherViewResult.Error::class.java))
        verify(forecastObserver,
               never()).onChanged(Mockito.any(ForecastViewResult.Error::class.java))
        verify(weatherObserver).onChanged(CurrentWeatherViewResult.Success(weather))
        verify(forecastObserver).onChanged(ForecastViewResult.Success(forecasts))
    }

    @Test
    fun `load weather data by city name with error`() {

        val weatherException = WeatherException()
        `when`(mainInteractor.getWeatherByCityName(anyString())).thenReturn(Single.error(weatherException))
        `when`(mainInteractor.getForecastByCityName(anyString())).thenReturn(Single.error(weatherException))
        `when`(resourceInteractor.getString(anyInt())).thenReturn("Error")

        viewModel?.searchStateData?.value = true
        viewModel?.searchText = "Test"
        viewModel?.loadData()


        verify(weatherObserver).onChanged(CurrentWeatherViewResult.Error("Error", weatherException))
        verify(forecastObserver).onChanged(ForecastViewResult.Error("Error", weatherException))
        verify(weatherObserver,
               never()).onChanged(Mockito.any(CurrentWeatherViewResult.Success::class.java))
        verify(forecastObserver,
               never()).onChanged(Mockito.any(ForecastViewResult.Success::class.java))
    }

    @Test
    fun `onSearchClick() change search state on opposite `() {
        viewModel?.searchStateData?.value = true
        viewModel?.onSearchClick()

        assert(viewModel?.searchStateData?.value == false)

        viewModel?.searchStateData?.value = false
        viewModel?.onSearchClick()

        assert(viewModel?.searchStateData?.value == true)
    }

    @Test
    fun `onUpdateClick() in search state - load data from current location`() {
        viewModel?.searchStateData?.value = true
        val weather = initCurrentWeatherData()
        val forecast = initForecastData()
        val forecasts = listOf(forecast, forecast)

       `when`(mainInteractor.getCurrentCityWeather(anyBoolean())).thenReturn(Single.just(weather))
       `when`(mainInteractor.getCurrentCityForecast(anyBoolean())).thenReturn(Single.just(forecasts))

        viewModel?.onUpdateClick()

        verify(mainInteractor)?.getCurrentCityWeather(false)
        verify(mainInteractor)?.getCurrentCityForecast(false)
    }

    @Test
    fun `onUpdateClick() in not search state - load data from cache`() {
        viewModel?.searchStateData?.value = false
        val weather = initCurrentWeatherData()
        val forecast = initForecastData()
        val forecasts = listOf(forecast, forecast)

       `when`(mainInteractor.getCurrentCityWeather(anyBoolean())).thenReturn(Single.just(weather))
       `when`(mainInteractor.getCurrentCityForecast(anyBoolean())).thenReturn(Single.just(forecasts))

        viewModel?.onUpdateClick()

        verify(mainInteractor)?.getCurrentCityWeather(true)
        verify(mainInteractor)?.getCurrentCityForecast(true)
    }

    @Test
    fun `onSearchAction() loads data by city name `() {
        val weather = initCurrentWeatherData()
        val forecast = initForecastData()
        val forecasts = listOf(forecast, forecast)

        `when`(mainInteractor.getWeatherByCityName(anyString())).thenReturn(Single.just(weather))
        `when`(mainInteractor.getForecastByCityName(anyString())).thenReturn(Single.just(forecasts))

        viewModel?.searchStateData?.value = true
        viewModel?.searchText = "Test"
        viewModel?.onSearchAction()

        verify(weatherObserver, never()).onChanged(Mockito.any(CurrentWeatherViewResult.Error::class.java))
        verify(forecastObserver, never()).onChanged(Mockito.any(ForecastViewResult.Error::class.java))
        verify(weatherObserver).onChanged(CurrentWeatherViewResult.Success(weather))
        verify(forecastObserver).onChanged(ForecastViewResult.Success(forecasts))
    }
}