package com.arashafsharpour.daresaymovie.features.splash.infrastructure

import com.arashafsharpour.daresaymovie.persistence.repositories.config.IConfigApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class SplashClientProvider {
    @Singleton
    @Provides
    fun provideConfigClient(
        retrofit: Retrofit
    ): IConfigApi {
        return retrofit.create(IConfigApi::class.java)
    }
}
