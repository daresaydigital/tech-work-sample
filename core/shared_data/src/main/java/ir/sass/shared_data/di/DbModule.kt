package ir.sass.shared_data.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.sass.shared_data.db.AppDatabase
import ir.sass.shared_data.db.MovieDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {
    @Provides
    @Singleton
    fun provideMovieDao(application: Application) : MovieDao{
        return AppDatabase.getDatabase(application).movieDao()
    }
}