package com.example.weatherapp.database.entities

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class City(@PrimaryKey val id: Long, val name: String, val country: String) : Parcelable {

    constructor(parcel: Parcel) : this(parcel.readLong(), parcel.readString() ?: "", parcel.readString() ?: "")

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeLong(id)
        dest?.writeString(name)
        dest?.writeString(country)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<City> {
        override fun createFromParcel(source: Parcel): City {
            return City(source)
        }

        override fun newArray(size: Int): Array<City?> {
            return arrayOfNulls(size)
        }
    }
}