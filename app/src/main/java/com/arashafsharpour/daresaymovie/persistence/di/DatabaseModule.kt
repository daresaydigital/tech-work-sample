package com.arashafsharpour.daresaymovie.persistence.di

import android.content.Context
import androidx.room.Room
import com.arashafsharpour.daresaymovie.persistence.database.roomdatabase.DaresayMovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): DaresayMovieDatabase {
        return Room.databaseBuilder(
            appContext,
            DaresayMovieDatabase::class.java,
            "DaresayMovie"
        ).fallbackToDestructiveMigration().build()
    }

//    @Provides
//    fun provideConfigDao(database: DaresayMovieDatabase): IConfigDao {
//        return database.configDao
//    }
}