package com.ukhanoff.rainbeforeseven

import com.ukhanoff.rainbeforeseven.di.AppComponent
import com.ukhanoff.rainbeforeseven.di.DaggerAppComponent
import dagger.android.AndroidInjector
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import dagger.android.support.DaggerApplication


class WeatherApplication : DaggerApplication() {

    private val injector: AppComponent by lazy {
        DaggerAppComponent.builder()
                .app(this)
                .build()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = injector


    override fun onCreate() {
        super.onCreate()
        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/RobotoCondensed-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build())

    }
}
