package com.daresaydigital.data.di

import android.content.Context
import androidx.room.Room
import com.daresaydigital.data.db.AppDatabase
import com.daresaydigital.data.features.favorite_movie.local.FavMovieDao
import com.daresaydigital.data.features.movie_details.local.MovieDetailsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * will provide Database and it's DAO objects for data layer
 */
@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "daresay-app-db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideFavMovieDao(appDatabase: AppDatabase): FavMovieDao {
        return appDatabase.favMovieDao()
    }

    @Singleton
    @Provides
    fun provideMovieDetailsDao(appDatabase: AppDatabase): MovieDetailsDao {
        return appDatabase.movieDetailsDao()
    }
}