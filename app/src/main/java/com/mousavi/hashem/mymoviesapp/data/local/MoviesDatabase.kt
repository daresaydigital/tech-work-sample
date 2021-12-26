package com.mousavi.hashem.mymoviesapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [MovieEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class MoviesDatabase: RoomDatabase() {
    abstract val dao: MovieDao

    companion object {
        const val DATABASE_NAME = "movies_db"
    }
}