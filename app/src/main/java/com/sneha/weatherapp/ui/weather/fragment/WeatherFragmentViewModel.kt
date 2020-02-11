package com.sneha.weatherapp.ui.weather.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.sneha.weatherapp.data.model.Weather
import com.sneha.weatherapp.data.repository.WeatherRepository
import com.sneha.weatherapp.ui.base.BaseViewModel
import com.sneha.weatherapp.utils.common.Resource
import com.sneha.weatherapp.utils.common.Status
import com.sneha.weatherapp.utils.network.NetworkHelper
import com.sneha.weatherapp.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable


class WeatherFragmentViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    private val weatherRepository: WeatherRepository
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {

    private val weatherLiveData: MutableLiveData<Resource<Weather>> = MutableLiveData()

    fun getWeatherData(): LiveData<Weather> =
        Transformations.map(weatherLiveData) { it.data }

    fun isWeatherFetching(): LiveData<Boolean> =
        Transformations.map(weatherLiveData) { it.status == Status.LOADING }

    override fun onCreate() {
        if (weatherLiveData.value == null && checkInternetConnectionWithMessage()) {
            weatherLiveData.postValue(Resource.loading())
            compositeDisposable.add(
                weatherRepository.fetchWeatherData()
                    .subscribeOn(schedulerProvider.io())
                    .subscribe(
                        { weatherLiveData.postValue(Resource.success(it)) },
                        {
                            handleNetworkError(it)
                            weatherLiveData.postValue(Resource.error())
                        })
            )
        }
    }
}