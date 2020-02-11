package com.sneha.weatherapp.ui.splash

import androidx.lifecycle.MutableLiveData
import com.sneha.weatherapp.data.repository.UserRepository
import com.sneha.weatherapp.ui.base.BaseViewModel
import com.sneha.weatherapp.utils.common.Event
import com.sneha.weatherapp.utils.network.NetworkHelper
import com.sneha.weatherapp.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    val userRepository: UserRepository
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {

    // Event is used by the view model to tell the activity to launch another Activity
    // view model also provided the Bundle in the event that is needed for the Activity
    val launchWeather: MutableLiveData<Event<Map<String, String>>> = MutableLiveData()

    init {
        GlobalScope.launch {
            delay(3000)
        }
    }

    override fun onCreate() {
        // Empty Bundle passed to Activity in Event that is needed by the other Activity
        // Here in actual application we will decide which screen to open based on
        // either the user is logged in or not
        launchWeather.postValue(Event(emptyMap()))
    }
}