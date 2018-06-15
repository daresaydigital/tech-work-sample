package com.ivy.weatherapp.data.local.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "weather")
data class Weather(
        @PrimaryKey var id: Long = 0,
        var name: String = "",
        var temp: Double = 0.0,
        @ColumnInfo(name = "temp_max") var tempMax: Int = 0,
        @ColumnInfo(name = "temp_min") var tempMin: Int = 0,
        var humidity: Int = 0,
        var description: String = "",
        var icon: String = "",
        var timestamp: Long = System.currentTimeMillis()
)