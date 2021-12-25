package com.arashafsharpour.daresaymovie

import android.content.Context
import androidx.multidex.BuildConfig
import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp

import io.github.inflationx.calligraphy3.CalligraphyConfig

import io.github.inflationx.calligraphy3.CalligraphyInterceptor

import io.github.inflationx.viewpump.ViewPump
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import timber.log.Timber

@HiltAndroidApp
class DaresayMovieApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(
                    CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                            .setDefaultFontPath("font/roboto_regular.ttf")
                            .setFontAttrId(R.font.roboto_regular)
                            .build()
                    )
                ).setCustomViewInflationEnabled(true)
                .build()
        )


        Timber.plant(Timber.DebugTree())

    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }
}