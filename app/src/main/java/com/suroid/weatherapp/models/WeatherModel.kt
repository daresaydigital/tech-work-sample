package com.suroid.weatherapp.models

import android.os.Parcel
import android.os.Parcelable

data class WeatherModel(
        var title: String? = null,
        var description: String? = null,
        var temperature: TemperatureModel? = null,
        var windSpeed: Float? = null,
        var humidity: Int? = null,
        var weather_id: Int? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readParcelable(TemperatureModel::class.java.classLoader),
            parcel.readFloat())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeParcelable(temperature, flags)
        parcel.writeFloat(windSpeed ?: 0.0f)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WeatherModel> {
        override fun createFromParcel(parcel: Parcel): WeatherModel {
            return WeatherModel(parcel)
        }

        override fun newArray(size: Int): Array<WeatherModel?> {
            return arrayOfNulls(size)
        }
    }
}