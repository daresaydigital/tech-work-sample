package com.arashafsharpour.daresaymovie.features.profile.infrastracture

import com.arashafsharpour.daresaymovie.persistence.repositories.profile.IProfileApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ProfileClientProvider {
    @Singleton
    @Provides
    fun provideProfileClient(
        retrofit: Retrofit
    ): IProfileApi {
        return retrofit.create(IProfileApi::class.java)
    }
}
