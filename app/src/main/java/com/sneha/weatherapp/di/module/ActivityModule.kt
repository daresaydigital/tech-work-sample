package com.sneha.weatherapp.di.module

import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sneha.weatherapp.data.repository.WeatherRepository
import com.sneha.weatherapp.data.repository.UserRepository
import com.sneha.weatherapp.ui.base.BaseActivity
import com.sneha.weatherapp.ui.weather.WeatherViewModel
import com.sneha.weatherapp.ui.splash.SplashViewModel
import com.sneha.weatherapp.utils.ViewModelProviderFactory
import com.sneha.weatherapp.utils.network.NetworkHelper
import com.sneha.weatherapp.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

/**
 * Kotlin Generics Reference: https://kotlinlang.org/docs/reference/generics.html
 * Basically it means that we can pass any class that extends BaseActivity which take
 * BaseViewModel subclass as parameter
 */
@Module
class ActivityModule(private val activity: BaseActivity<*>) {

    @Provides
    fun provideLinearLayoutManager(): LinearLayoutManager = LinearLayoutManager(activity)

    @Provides
    fun provideSplashViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        userRepository: UserRepository
    ): SplashViewModel = ViewModelProviders.of(
        activity, ViewModelProviderFactory(SplashViewModel::class) {
            SplashViewModel(schedulerProvider, compositeDisposable, networkHelper, userRepository)
            //this lambda creates and return SplashViewModel
        }).get(SplashViewModel::class.java)

    @Provides
    fun provideWeatherViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        weatherRepository: WeatherRepository
    ): WeatherViewModel = ViewModelProviders.of(
        activity, ViewModelProviderFactory(WeatherViewModel::class) {
            WeatherViewModel(schedulerProvider, compositeDisposable, networkHelper, weatherRepository)
        }).get(WeatherViewModel::class.java)
}