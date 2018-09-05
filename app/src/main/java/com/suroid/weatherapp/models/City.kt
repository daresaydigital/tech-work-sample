package com.suroid.weatherapp.models

import android.arch.persistence.room.Entity
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["id"])
data class City(
        @field:SerializedName("name")
        val name: String,
        @field:SerializedName("country")
        val country: String,
        @field:SerializedName("id")
        val id: Int): Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString() ?: "",
            parcel.readString() ?: "",
            parcel.readInt()) {
    }

    override fun equals(other: Any?): Boolean {
        return other is City && other.id == id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(country)
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<City> {
        override fun createFromParcel(parcel: Parcel): City {
            return City(parcel)
        }

        override fun newArray(size: Int): Array<City?> {
            return arrayOfNulls(size)
        }
    }
}