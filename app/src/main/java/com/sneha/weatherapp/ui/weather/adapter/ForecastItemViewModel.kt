package com.sneha.weatherapp.ui.weather.adapter

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.sneha.weatherapp.data.model.Forecast
import com.sneha.weatherapp.ui.base.BaseItemViewModel
import com.sneha.weatherapp.utils.common.TimeUtils
import com.sneha.weatherapp.utils.log.Logger
import com.sneha.weatherapp.utils.network.NetworkHelper
import com.sneha.weatherapp.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ForecastItemViewModel @Inject constructor(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper
) : BaseItemViewModel<Forecast.Item>(
    schedulerProvider,
    compositeDisposable,
    networkHelper
) {

    companion object {
        const val TAG = "ForecastItemViewModel"
    }

    val time: LiveData<String> =
        Transformations.map(data) { TimeUtils.changeDateToHourFormat(it.dt_txt) }
    val feelsLike: LiveData<String> = Transformations.map(data) { it.weather[0].description }
    val icon: LiveData<String> = Transformations.map(data) { it.weather[0].icon }

    fun onItemClick(position: Int) {
//        messageString.postValue(Resource.success("onItemClick at $position of ${data.value?.time}"))
//        Logger.d(TAG, "onItemClick at $position")
    }

    override fun onCreate() {
        Logger.d(TAG, "onCreate called")
    }
}