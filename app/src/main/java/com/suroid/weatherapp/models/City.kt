package com.suroid.weatherapp.models

import android.arch.persistence.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["id"])
data class City(
        @field:SerializedName("name")
        val name: String,
        @field:SerializedName("country")
        val country: String,
        @field:SerializedName("id")
        val id: Int) {

    override fun equals(other: Any?): Boolean {
        return other is City && other.id == id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}