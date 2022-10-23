package com.github.hramogin.movieapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.hramogin.movieapp.data.database.model.MovieDb
import com.github.hramogin.movieapp.data.database.model.MovieDetailsDb
import com.github.hramogin.movieapp.data.database.model.MovieReviewsDb
import com.github.hramogin.movieapp.data.repository.movie.DaoMovie

@Database(
    entities = [MovieDb::class, MovieDetailsDb::class, MovieReviewsDb::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun moviesDao(): DaoMovie
}