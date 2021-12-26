package com.arashafsharpour.daresaymovie.persistence.repositories.profile

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProfileModuleProvider {
    @Provides
    @Singleton
    fun provideConfigRepository(dataSource: ProfileRepository): IProfileRepository = dataSource
}