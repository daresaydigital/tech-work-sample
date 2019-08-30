package com.example.weatherapp.database.entities

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class City(@PrimaryKey val id: Long, val name: String, val country: String, val shiftTimezone: Long? = null) :
    Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readValue(Long::class.java.classLoader) as Long?
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeLong(id)
        dest?.writeString(name)
        dest?.writeString(country)
        shiftTimezone?.let {
            dest?.writeLong(it)
        }
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