package com.sneha.weatherapp.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.sneha.weatherapp.data.model.Weather
import com.sneha.weatherapp.data.remote.response.DailyForecastResponse
import com.sneha.weatherapp.data.remote.response.TodayForecastResponse
import com.sneha.weatherapp.data.repository.WeatherRepository
import com.sneha.weatherapp.ui.base.BaseViewModel
import com.sneha.weatherapp.utils.common.Resource
import com.sneha.weatherapp.utils.common.Status
import com.sneha.weatherapp.utils.network.NetworkHelper
import com.sneha.weatherapp.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable


class WeatherViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    private val weatherRepository: WeatherRepository
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {

    private val weatherLiveData: MutableLiveData<Resource<Weather>> = MutableLiveData()
    private val forecastLiveData: MutableLiveData<Resource<TodayForecastResponse>> = MutableLiveData()
    private val dailyForecastLiveData: MutableLiveData<Resource<DailyForecastResponse>> = MutableLiveData()

    fun getWeatherData(): LiveData<Weather> =
        Transformations.map(weatherLiveData) {
            it.data
        }

    fun getForecastData(): LiveData<TodayForecastResponse> = Transformations.map(forecastLiveData) {
        it.data
    }

    fun getDailyForecastData(): LiveData<DailyForecastResponse> = Transformations.map(dailyForecastLiveData) {
            it.data
        }

    fun isWeatherFetching(): LiveData<Boolean> =
        Transformations.map(weatherLiveData) { it.status == Status.LOADING }

    override fun onCreate() {
        callWeatherApi()

        if (forecastLiveData.value == null && checkInternetConnectionWithMessage()) {
            forecastLiveData.postValue(Resource.loading())
            compositeDisposable.add(
                weatherRepository.fetchTodayForecast(2673730)
                    .subscribeOn(schedulerProvider.io())
                    .subscribe(
                        { forecastLiveData.postValue(Resource.success(it)) },
                        {
                            handleNetworkError(it)
                            forecastLiveData.postValue(Resource.error())
                        })
            )
        }

        if (dailyForecastLiveData.value == null && checkInternetConnectionWithMessage()) {
            dailyForecastLiveData.postValue(Resource.loading())
            compositeDisposable.add(
                weatherRepository.fetchDailyForecast(2673730)
                    .subscribeOn(schedulerProvider.io())
                    .subscribe(
                        { dailyForecastLiveData.postValue(Resource.success(it)) },
                        {
                            handleNetworkError(it)
                            dailyForecastLiveData.postValue(Resource.error())
                        })
            )
        }
    }

    private fun callWeatherApi() {
        compositeDisposable.add(
            weatherRepository.fetchWeatherData("Stockholm,SE")
                .subscribeOn(schedulerProvider.io())
                .subscribe({
                    weatherLiveData.postValue(Resource.success(it))
                },
                    {
                        handleNetworkError(it)
                        weatherLiveData.postValue(Resource.error())
                    })
        )
    }

    fun refreshData() {
        callWeatherApi()
    }
}