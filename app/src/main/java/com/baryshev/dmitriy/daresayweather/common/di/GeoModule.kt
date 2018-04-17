package com.baryshev.dmitriy.daresayweather.common.di

import android.content.Context
import com.baryshev.dmitriy.daresayweather.common.data.geo.GeoRepo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * 4/12/2018.
 */
@Module
class GeoModule {
    @Singleton
    @Provides
    fun provideGeoRepo(context: Context): GeoRepo = GeoRepo(context)
}