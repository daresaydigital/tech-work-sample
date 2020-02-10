package com.sneha.weatherapp.ui.weather.adapter

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.sneha.weatherapp.data.model.DailyForecast
import com.sneha.weatherapp.ui.base.BaseItemViewModel
import com.sneha.weatherapp.utils.common.Resource
import com.sneha.weatherapp.utils.common.TimeUtils
import com.sneha.weatherapp.utils.display.WeatherToImage
import com.sneha.weatherapp.utils.log.Logger
import com.sneha.weatherapp.utils.network.NetworkHelper
import com.sneha.weatherapp.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class DailyForecastItemViewModel @Inject constructor(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper
) : BaseItemViewModel<DailyForecast.ForecastItem>(
    schedulerProvider,
    compositeDisposable,
    networkHelper
) {

    companion object {
        const val TAG = "DailyForecastItemViewModel"
    }

    val forecastItem: LiveData<DailyForecast.ForecastItem> = Transformations.map(data) {it}
    val time: LiveData<String> =
        Transformations.map(data) { TimeUtils.getTimeInExpectedFormat(it.dt, "EEE") }
    val feelsLike: LiveData<String> = Transformations.map(data) { it.weather[0].description }
    val icon: LiveData<Int> = Transformations.map(data) { WeatherToImage.getImageForCode(it.weather[0].icon) }
    val temp: LiveData<String> = Transformations.map(data) {
        "${it.temp.min.toInt()}℃ / ${it.temp.max.toInt()}℃"
    }

    fun onItemClick(position: Int) {
//        messageString.postValue(Resource.success("onItemClick at $position of ${data.value?.temp?.max?.toInt()}"))
//        Logger.d(TAG, "onItemClick at $position")
    }

    override fun onCreate() {
        Logger.d(TAG, "onCreate called")
    }
}