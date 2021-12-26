package com.daresaydigital.data.di

import com.daresaydigital.core.utils.GlobalDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
class TestCoreModule {

    @Provides
    fun provideGlobalDispatcher() = GlobalDispatcher(
        main = Dispatchers.Unconfined,
        io = Dispatchers.Unconfined,
        default = Dispatchers.Unconfined
    )
}