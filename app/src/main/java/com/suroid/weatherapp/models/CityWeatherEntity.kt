package com.suroid.weatherapp.models

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

@Entity
data class CityWeatherEntity(@PrimaryKey val id: Int,
                             val currentWeather: WeatherModel?,
                             val date: Long = 0,
                             val dailyForecast: List<TemperatureModel> = ArrayList(),
                             val hourlyForecast: List<TemperatureModel> = ArrayList(),
                             @Embedded(prefix = "city") val city: City) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readParcelable(WeatherModel::class.java.classLoader),
            parcel.readLong(),
            parcel.createTypedArrayList(TemperatureModel) as? ArrayList<TemperatureModel>
                    ?: ArrayList(),
            parcel.createTypedArrayList(TemperatureModel)as? ArrayList<TemperatureModel>
                    ?: ArrayList(),
            parcel.readParcelable(City::class.java.classLoader) ?: City("", "", 0)) {
    }

    override fun equals(other: Any?): Boolean {
        return other is CityWeatherEntity && other.id == id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeParcelable(currentWeather, flags)
        parcel.writeLong(date)
        parcel.writeTypedList(dailyForecast)
        parcel.writeTypedList(hourlyForecast)
        parcel.writeParcelable(city, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CityWeatherEntity> {
        override fun createFromParcel(parcel: Parcel): CityWeatherEntity {
            return CityWeatherEntity(parcel)
        }

        override fun newArray(size: Int): Array<CityWeatherEntity?> {
            return arrayOfNulls(size)
        }
    }
}
