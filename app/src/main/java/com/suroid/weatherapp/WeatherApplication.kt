package com.suroid.weatherapp

import android.app.Activity
import android.app.Application
import com.suroid.weatherapp.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.reactivex.plugins.RxJavaPlugins
import javax.inject.Inject

class WeatherApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()

        AppInjector.init(this)

        RxJavaPlugins.setErrorHandler { error ->
            //Unexpected RXJAVA errors should be handled here to prevent crashes
            error.printStackTrace()
        }
    }
}