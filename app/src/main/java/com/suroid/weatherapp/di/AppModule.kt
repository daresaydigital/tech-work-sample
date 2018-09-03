package com.suroid.weatherapp.di

import android.app.Application
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.suroid.weatherapp.db.CityDao
import com.suroid.weatherapp.db.WeatherDb
import com.suroid.weatherapp.models.City
import com.suroid.weatherapp.utils.CITIES_JSON_FILE_NAME
import com.suroid.weatherapp.utils.CITY_ARRAY_LIST_TYPE
import com.suroid.weatherapp.utils.loadJSONFromAsset
import com.suroid.weatherapp.utils.objectify
import dagger.Module
import dagger.Provides
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

/**
 * Module which provides application level dependencoes
 * @param app [Application] instance is required as parameter
 */
@Module
class AppModule(private val app: Application) {

    lateinit var weatherDb: WeatherDb

    init {
        weatherDb = Room
                .databaseBuilder(app, WeatherDb::class.java, "weather.db")
                .fallbackToDestructiveMigration()
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        //Prepopulate DataBase with list of cities

                        Completable.fromCallable {
                            val cities: List<City>? = loadJSONFromAsset(CITIES_JSON_FILE_NAME)?.objectify(CITY_ARRAY_LIST_TYPE)
                            cities?.let {
                                weatherDb.cityDao().insert(it)
                            }
                        }.subscribeOn(Schedulers.io()).subscribe()
                    }
                })
                .build()

        //Fake call to initialize DB
        weatherDb.cityDao().getAllCities().subscribeOn(Schedulers.io()).subscribe()
    }

    /**
     * Provides application Context
     * @return [Context]
     */
    @Provides
    @Singleton
    fun providesContext(): Context {
        return app
    }

    /**
     * Provides WeatherDB
     * @return [WeatherDb]
     */
    @Singleton
    @Provides
    fun provideDb(): WeatherDb {
        return weatherDb
    }

    /**
     * Provides CityDao
     * @param db [WeatherDb] is required as parameter
     * @return [CityDao]
     */
    @Singleton
    @Provides
    fun provideCityDao(db: WeatherDb): CityDao {
        return db.cityDao()
    }
}