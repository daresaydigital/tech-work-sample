package com.arashafsharpour.daresaymovie.persistence.repositories.config

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ConfigModuleProvider {

    @Provides
    @Singleton
    fun provideConfigRepository(dataSource: ConfigRepository) : IConfigRepository = dataSource

}