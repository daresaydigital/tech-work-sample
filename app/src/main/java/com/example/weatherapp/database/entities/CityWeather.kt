package com.example.weatherapp.database.entities

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weatherapp.models.Weather
import java.util.*

@Entity
data class CityWeather(@PrimaryKey val id: Long, val weather: Weather, val lastUpdateClient: Long = Date().time, val lastUpdateServer: Long, @Embedded(prefix = "city_") val city: City) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readParcelable(Weather::class.java.classLoader),
        parcel.readLong(),
        parcel.readLong(),
        parcel.readParcelable(City::class.java.classLoader)
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeLong(id)
        dest?.writeParcelable(weather, flags)
        dest?.writeLong(lastUpdateClient)
        dest?.writeLong(lastUpdateServer)
        dest?.writeParcelable(city, flags)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<CityWeather> {
        override fun createFromParcel(source: Parcel): CityWeather {
            return CityWeather(source)
        }

        override fun newArray(size: Int): Array<CityWeather?> {
            return arrayOfNulls(size)
        }
    }
}