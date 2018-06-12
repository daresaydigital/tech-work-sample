package com.ivy.weatherapp.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "weather")
data class Weather(
        @PrimaryKey val id: Long,
        val name: String,
        val temp: Double,
        @ColumnInfo(name = "temp_max") val tempMax: Int,
        @ColumnInfo(name = "temp_min") val tempMin: Int,
        val humidity: Int,
        val description: String,
        val icon: String,
        val timestamp: Long
)