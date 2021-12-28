package com.movies.tmdb.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.movies.tmdb.data.remote.responses.MovieObject

@Database(
    entities = [MovieObject::class],
    version = 1
)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao
}