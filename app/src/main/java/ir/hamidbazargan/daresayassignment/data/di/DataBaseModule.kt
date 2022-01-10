package ir.hamidbazargan.daresayassignment.data.di

import androidx.room.Room
import ir.hamidbazargan.daresayassignment.data.db.MovieDataBase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataBaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            MovieDataBase::class.java, "movies_db"
        ).build()
    }
}