package com.daresaydigital.common.di.module

import com.daresaydigital.common.utils.GlobalDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
class CoreModule {

    @Provides
    fun provideGlobalDispatcher() = GlobalDispatcher(
        main = Dispatchers.Main,
        io = Dispatchers.IO,
        default = Dispatchers.Default
    )
}