package com.suroid.weatherapp.di

import android.app.Application
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.suroid.weatherapp.db.CityDao
import com.suroid.weatherapp.db.WeatherDb
import com.suroid.weatherapp.models.CITY_ARRAY_LIST_TYPE
import com.suroid.weatherapp.models.City
import com.suroid.weatherapp.utils.CITIES_JSON_FILE_NAME
import com.suroid.weatherapp.utils.loadJSONFromAsset
import com.suroid.weatherapp.utils.objectify
import dagger.Module
import dagger.Provides
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

/**
 * Module which provides context Dependency
 * @param context Application context
 */
@Module
class AppModule(private val app: Application) {

    lateinit var weatherDb: WeatherDb

    init {
        weatherDb = Room
                .databaseBuilder(app, WeatherDb::class.java, "weather.db")
                .fallbackToDestructiveMigration()
                .addCallback(object: RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        //Prepopulate DataBase with list of cities
                        Single.fromCallable {
                            val cities: List<City>? = loadJSONFromAsset(CITIES_JSON_FILE_NAME)?.objectify(CITY_ARRAY_LIST_TYPE)
                            cities?.let {
                                weatherDb.cityDao().insert(it)
                            }
                        }.subscribeOn(Schedulers.io()).subscribe()
                    }
                })
                .build()

        //Fake call to initialize DB
        Single.fromCallable {
            weatherDb.cityDao().getData()
        }.subscribeOn(Schedulers.io()).subscribe()
    }

    /**
     * Provides application Context
     * @return ApplicationContext
     */
    @Provides
    @Singleton
    fun providesContext(): Context {
        return app
    }

    @Singleton
    @Provides
    fun provideDb(): WeatherDb {
        return weatherDb
    }

    @Singleton
    @Provides
    fun provideCityDao(db: WeatherDb): CityDao {
        return db.cityDao()
    }
}