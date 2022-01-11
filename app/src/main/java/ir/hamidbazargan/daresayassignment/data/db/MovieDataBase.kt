package ir.hamidbazargan.daresayassignment.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.hamidbazargan.daresayassignment.data.db.entity.MovieDataBaseEntity

@Database(entities = [MovieDataBaseEntity::class], version = 1)
abstract class MovieDataBase : RoomDatabase() {
    abstract fun MovieDao(): MovieDao
}