package com.daresaydigital.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.daresaydigital.data.features.favorite_movie.FavMovieDao
import com.daresaydigital.data.features.movie_details.MovieDetailsLocalDao
import com.daresaydigital.data.model.entity.FavMovieLocalEntity
import com.daresaydigital.data.model.entity.MovieDetailsLocalEntity

@Database(entities = [FavMovieLocalEntity::class,MovieDetailsLocalEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favMovieDao(): FavMovieDao

    abstract fun movieDetailsDao(): MovieDetailsLocalDao
}