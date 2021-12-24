package com.mousavi.hashem.mymoviesapp.di

import android.util.Log
import com.mousavi.hashem.mymoviesapp.BuildConfig
import com.mousavi.hashem.mymoviesapp.data.remote.Api
import com.mousavi.hashem.mymoviesapp.data.remote.Api.Companion.API_KEY
import com.mousavi.hashem.mymoviesapp.data.remote.Api.Companion.BASE_URL
import com.mousavi.hashem.mymoviesapp.data.remote.NetworkDataSource
import com.mousavi.hashem.mymoviesapp.data.remote.NetworkDataSourceImpl
import com.mousavi.hashem.mymoviesapp.data.repository.MoviesRepositoryImpl
import com.mousavi.hashem.mymoviesapp.domain.repository.MoviesRepository
import com.mousavi.hashem.mymoviesapp.domain.usecases.GetGenres
import com.mousavi.hashem.mymoviesapp.domain.usecases.GetPopularMovies
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideNetworkDataSource(api: Api): NetworkDataSource {
        return NetworkDataSourceImpl(api)
    }

    @Provides
    @Singleton
    fun provideMoviesRepository(repository: MoviesRepositoryImpl): MoviesRepository {
        return repository
    }

    @Provides
    @Singleton
    fun provideGetPopularMoviesUseCase(repository: MoviesRepository): GetPopularMovies {
        return GetPopularMovies(repository)
    }

    @Provides
    @Singleton
    fun provideGetGenresUseCase(repository: MoviesRepository): GetGenres {
        return GetGenres(repository)
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