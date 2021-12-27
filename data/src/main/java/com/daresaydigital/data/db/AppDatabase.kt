package com.daresaydigital.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.daresaydigital.data.features.favorite_movie.local.FavMoviesDao
import com.daresaydigital.data.features.favorite_movie.model.FavDbTypeConverter
import com.daresaydigital.data.features.movie_details.local.MovieDetailsDao
import com.daresaydigital.data.features.favorite_movie.model.FavMovieLocalEntity
import com.daresaydigital.data.features.movie_details.model.MovieDetailsDbTypeConverter
import com.daresaydigital.data.features.movie_details.model.MovieDetailsLocalEntity

@Database(entities = [FavMovieLocalEntity::class, MovieDetailsLocalEntity::class], version = 1)
@TypeConverters(FavDbTypeConverter::class, MovieDetailsDbTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favMovieDao(): FavMoviesDao

    abstract fun movieDetailsDao(): MovieDetailsDao
}