package ir.sass.data.model.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.sass.data.model.datasource.remote.DiscoverMovieApi
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Provides
    fun provideOkHttpClient() : OkHttpClient {
        val okClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                var request: Request = chain.request()
                val url: HttpUrl = request.url().newBuilder()
                    .addQueryParameter("api_key", "500b07a7f284efab4476a03d5fc384e1")
                    .build()
                request = request.newBuilder().url(url).build()
                chain.proceed(request)
            }
            .build()
        return okClient
    }

    @Provides
    @Singleton
    fun provideRetrofit(httpClient : OkHttpClient) : Retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
        .build()

    @Provides
    @Singleton
    fun provideDiscoverMovieApi(retrofit: Retrofit) : DiscoverMovieApi = retrofit
        .create(DiscoverMovieApi::class.java)
}