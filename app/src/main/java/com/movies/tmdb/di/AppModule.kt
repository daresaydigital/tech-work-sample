package com.movies.tmdb.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.movies.tmdb.R
import com.movies.tmdb.data.local.MoviesDao
import com.movies.tmdb.data.local.MoviesDatabase
import com.movies.tmdb.data.remote.TmdbApi
import com.movies.tmdb.other.Constants.BASE_URL
import com.movies.tmdb.other.Constants.DATABASE_NAME
import com.movies.tmdb.repositories.DefaultMoviesRepository
import com.movies.tmdb.repositories.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideMoviesDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, MoviesDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideDefaultMoviesRepository(

        dao: MoviesDao,
        api: TmdbApi
    ) = DefaultMoviesRepository(dao, api) as MoviesRepository


    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideGlideInstance(
        @ApplicationContext context: Context
    ) = Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
            .placeholder(R.drawable.ic_image)
            .error(R.drawable.ic_image)
    )

    @Singleton
    @Provides
    fun provideMoviesDao(
        database: MoviesDatabase
    ) = database.moviesDao()

    @Singleton
    @Provides
    fun provideTmdbApi(okHttpClient: OkHttpClient): TmdbApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build()
            .create(TmdbApi::class.java)
    }
}

















