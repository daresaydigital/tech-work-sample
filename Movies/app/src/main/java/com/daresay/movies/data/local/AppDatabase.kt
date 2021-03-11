package com.daresay.movies.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.daresay.movies.data.models.favorites.Favorite
import com.daresay.movies.data.models.moviedetails.MovieDetails

@Database(entities = [MovieDetails::class, Favorite::class], version = 1, exportSchema = false)
@TypeConverters(MyTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao() : MovieDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context) : AppDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, "Movies")
                .fallbackToDestructiveMigration()
                .build()
    }
}