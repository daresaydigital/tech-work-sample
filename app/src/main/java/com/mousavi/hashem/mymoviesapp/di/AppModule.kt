package com.mousavi.hashem.mymoviesapp.di

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.room.Room
import com.mousavi.hashem.mymoviesapp.BuildConfig
import com.mousavi.hashem.mymoviesapp.data.local.MovieDao
import com.mousavi.hashem.mymoviesapp.data.local.MoviesDatabase
import com.mousavi.hashem.mymoviesapp.data.local.MoviesDatabase.Companion.DATABASE_NAME
import com.mousavi.hashem.mymoviesapp.data.remote.*
import com.mousavi.hashem.mymoviesapp.data.remote.Api.Companion.API_KEY
import com.mousavi.hashem.mymoviesapp.data.remote.Api.Companion.BASE_URL
import com.mousavi.hashem.mymoviesapp.data.repository.MoviesRepositoryImpl
import com.mousavi.hashem.mymoviesapp.domain.repository.MoviesRepository
import com.mousavi.hashem.mymoviesapp.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {
        val logging = HttpLoggingInterceptor { message ->
            Log.d("okHttpLog", message)
        }.apply {
            setLevel(
                if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BASIC
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            )
        }

        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                return@addInterceptor addApiKeyToRequests(chain)
            }
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitApi(client: OkHttpClient): Api {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }

    @Provides
    @Singleton
    fun provideNetworkDataSource(api: Api, stringProvider: StringProvider): NetworkDataSource {
        return NetworkDataSourceImpl(api, stringProvider)
    }

    @Provides
    fun provideStringProvider(@ApplicationContext appContext: Context): StringProvider {
        return StringProviderImpl(appContext)
    }

    @Provides
    @Singleton
    fun provideMoviesRepository(repository: MoviesRepositoryImpl): MoviesRepository {
        return repository
    }

    @Provides
    fun provideGetPopularMoviesUseCase(
        repository: MoviesRepository,
        getGenresUseCase: GetGenresUseCase,
    ): GetPopularMoviesUseCase {
        return GetPopularMoviesUseCaseImpl(repository, getGenresUseCase)
    }

    @Provides
    fun provideGetGenresUseCase(repository: MoviesRepository): GetGenresUseCase {
        return GetGenresUseCaseImpl(repository)
    }

    @Provides
    @Singleton
    fun provideRoomDatabase(app: Application): MoviesDatabase {
        return Room.databaseBuilder(
            app,
            MoviesDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(moviesDatabase: MoviesDatabase): MovieDao {
        return moviesDatabase.dao
    }

    @Provides
    @Singleton
    fun provideGetFavoriteMoviesFromDatabaseUseCase(repository: MoviesRepository): GetFavoriteMoviesFromDatabaseUseCase {
        return GetFavoriteMoviesFromDatabaseUseCaseImpl(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteFavoriteMovieUseCase(repository: MoviesRepository): DeleteFavoriteMovieUseCase {
        return DeleteFavoriteMovieUseCaseImpl(repository)
    }

    @Provides
    @Singleton
    fun provideCheckIfFavoriteUseCase(repository: MoviesRepository): CheckIfFavoriteUseCase {
        return CheckIfFavoriteUseCaseImpl(repository)
    }

    @Provides
    @Singleton
    fun provideSaveFavoriteMovieToDatabaseUseCase(repository: MoviesRepository): SaveFavoriteMovieToDatabaseUseCase {
        return SaveFavoriteMovieToDatabaseUseCaseImpl(repository)
    }

    @Provides
    @Singleton
    fun provideGetReviewsUseCase(repository: MoviesRepository): GetReviewsUseCase {
        return GetReviewsUseCaseImpl(repository)
    }
}

private fun addApiKeyToRequests(chain: Interceptor.Chain): Response {
    val request = chain.request().newBuilder()
    val originalHttpUrl = chain.request().url
    val newUrl = originalHttpUrl.newBuilder()
        .addQueryParameter("api_key", API_KEY).build()
    request.url(newUrl)
    return chain.proceed(request.build())
}