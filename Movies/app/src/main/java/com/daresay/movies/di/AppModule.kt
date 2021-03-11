package com.daresay.movies.di

import android.content.Context
import com.daresay.movies.data.api.TmdbRemoteDataSource
import com.daresay.movies.data.api.TmdbRepository
import com.daresay.movies.data.api.TmdbService
import com.daresay.movies.data.local.AppDatabase
import com.daresay.movies.data.local.MovieDao
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        )

        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient.build())
            .build()
    }

    @Provides
    fun provideGson() : Gson {
        return GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create()
    }

    @Provides
    fun provideTmdbService(retrofit: Retrofit): TmdbService = retrofit.create(TmdbService::class.java)

    @Singleton
    @Provides
    fun provideTmdbRemoteDataSource(tmdbService: TmdbService) = TmdbRemoteDataSource(tmdbService)

    @Singleton
    @Provides
    fun provideTmdbRepository(remoteDataSource: TmdbRemoteDataSource, localDataSource: MovieDao) =
        TmdbRepository(remoteDataSource, localDataSource)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideMovieDetailsDao(db: AppDatabase) = db.movieDao()
}