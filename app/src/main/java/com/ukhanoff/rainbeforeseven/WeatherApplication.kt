package com.ukhanoff.rainbeforeseven

import android.app.Application
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

class WeatherApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/RobotoCondensed-Regular.ttf")
                .setFontAttrId(R.attr.fontPath).build())

    }
}