package com.suroid.weatherapp.models

import android.os.Parcel
import android.os.Parcelable

data class TemperatureModel(
        var temp: Float? = null,
        var minTemp: Float? = null,
        var maxTemp: Float? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readFloat(),
            parcel.readFloat(),
            parcel.readFloat()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeFloat(temp ?: 0.0f)
        parcel.writeFloat(minTemp ?: 0.0f)
        parcel.writeFloat(maxTemp ?: 0.0f)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TemperatureModel> {
        override fun createFromParcel(parcel: Parcel): TemperatureModel {
            return TemperatureModel(parcel)
        }

        override fun newArray(size: Int): Array<TemperatureModel?> {
            return arrayOfNulls(size)
        }
    }
}