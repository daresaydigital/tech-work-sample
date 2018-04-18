package com.baryshev.dmitriy.daresayweather.common.di

import android.content.Context
import com.baryshev.dmitriy.daresayweather.common.data.cache.CacheRepo
import com.baryshev.dmitriy.daresayweather.common.data.network.Api
import com.baryshev.dmitriy.daresayweather.common.data.network.ApiService
import com.baryshev.dmitriy.daresayweather.common.domain.IResourceInteractor
import com.baryshev.dmitriy.daresayweather.common.domain.ResourceInteractor
import com.baryshev.dmitriy.daresayweather.utils.rx.IRxScheduler
import com.baryshev.dmitriy.daresayweather.utils.rx.RxScheduler
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val appContext: Context) {

    @Singleton
    @Provides
    fun provideContext(): Context = appContext

    @Singleton
    @Provides
    fun provideRxScheduler(): IRxScheduler = RxScheduler()

    @Singleton
    @Provides
    fun provideResourceInteractor(): IResourceInteractor = ResourceInteractor(appContext)

    @Singleton
    @Provides
    fun provideRetrofit(): Api = ApiService().init()

    @Singleton
    @Provides
    fun provideCacheRepo():CacheRepo = CacheRepo()
}