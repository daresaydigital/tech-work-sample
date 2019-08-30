package com.example.weatherapp.models

import android.os.Parcel
import android.os.Parcelable

data class Weather(
    val name: String,
    val description: String,
    val icon: String,
    val pressure: Int,
    val humidity: Int,
    val temp: Double,
    val minTemp: Double,
    val maxTemp: Double,
    val weatherId: Int
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readInt()
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(name)
        dest?.writeString(description)
        dest?.writeString(icon)
        dest?.writeInt(pressure)
        dest?.writeInt(humidity)
        dest?.writeDouble(temp)
        dest?.writeDouble(minTemp)
        dest?.writeDouble(maxTemp)
        dest?.writeInt(weatherId)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Weather> {
        override fun createFromParcel(source: Parcel): Weather {
            return Weather(source)
        }

        override fun newArray(size: Int): Array<Weather?> {
            return arrayOfNulls(size)
        }
    }
}