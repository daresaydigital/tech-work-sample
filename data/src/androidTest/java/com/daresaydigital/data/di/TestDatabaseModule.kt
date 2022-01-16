package com.daresaydigital.data.di

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.daresaydigital.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TestDatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(): AppDatabase {
        return Room.databaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            AppDatabase::class.java,
            "daresay-app-db"
        ).fallbackToDestructiveMigration()
            .build()
    }

}