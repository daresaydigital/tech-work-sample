package com.github.hramogin.movieapp.di.modules

import android.content.Context
import androidx.room.Room
import com.github.hramogin.movieapp.data.database.AppDatabase
import org.koin.dsl.module

const val DATABASE_NAME = "APP_DATABASE"

val databaseModule = module {
    single { Room.databaseBuilder(get(), AppDatabase::class.java, DATABASE_NAME).build() }
    single { get<Context>().cacheDir }

    single { get<AppDatabase>().moviesDao() }
}