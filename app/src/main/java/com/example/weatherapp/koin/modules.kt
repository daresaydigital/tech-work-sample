package com.example.weatherapp.koin

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.weatherapp.database.WeatherDatabase
import com.example.weatherapp.services.WeatherService
import com.example.weatherapp.utils.BASE_URL
import com.example.weatherapp.utils.jsonUtils.Companion.getCitiesFromAsset
import com.example.weatherapp.utils.runOnIoThread
import com.example.weatherapp.viewModels.CityAddViewModel
import com.example.weatherapp.viewModels.WeatherViewModel
import com.google.android.gms.location.LocationServices
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { provideDefaultOkhttpClient() }
    single { provideRetrofit(get()) }
    single { provideWeatherService(get()) }
}

val databaseModule = module {
    single {
        Room.databaseBuilder(androidApplication(), WeatherDatabase::class.java, "weather-db")
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    runOnIoThread {
                        get<WeatherDatabase>().cityDao().insertAll(getCitiesFromAsset(androidApplication()))
                    }
                }
            })
            .build()
    }
    single { get<WeatherDatabase>().cityDao() }
}

val viewModelModule = module {
    viewModel { WeatherViewModel(get(), LocationServices.getFusedLocationProviderClient(androidApplication())) }
    viewModel { CityAddViewModel(get()) }
}

fun provideDefaultOkhttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .build()
}

fun provideRetrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideWeatherService(retrofit: Retrofit): WeatherService = retrofit.create(WeatherService::class.java)