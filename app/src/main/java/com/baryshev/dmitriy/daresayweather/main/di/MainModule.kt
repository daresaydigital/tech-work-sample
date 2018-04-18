package com.baryshev.dmitriy.daresayweather.main.di

import com.baryshev.dmitriy.daresayweather.common.data.cache.CacheRepo
import com.baryshev.dmitriy.daresayweather.common.data.geo.GeoRepo
import com.baryshev.dmitriy.daresayweather.common.data.weather.WeatherRepo
import com.baryshev.dmitriy.daresayweather.common.domain.IResourceInteractor
import com.baryshev.dmitriy.daresayweather.main.domain.IMainInteractor
import com.baryshev.dmitriy.daresayweather.main.domain.MainInteractor
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @Provides
    @MainScope
    fun provideMainInteractor(cacheRepo: CacheRepo,
                              geoRepo: GeoRepo,
                              weatherRepo: WeatherRepo,
                              resourceInteractor: IResourceInteractor): IMainInteractor =
        MainInteractor(cacheRepo, geoRepo, weatherRepo, resourceInteractor)


}