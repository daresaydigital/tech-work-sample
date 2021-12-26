package com.daresaydigital.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.daresaydigital.data.features.favorite_movie.FavMovieDao
import com.daresaydigital.data.model.FavMovieLocalEntity

@Database(entities = [FavMovieLocalEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favMovieDao(): FavMovieDao
}