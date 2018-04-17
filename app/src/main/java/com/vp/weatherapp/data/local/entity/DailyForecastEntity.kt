package com.vp.weatherapp.data.local.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "daily_forecast")
data class DailyForecastEntity @Ignore constructor(
        @PrimaryKey(autoGenerate = true)    var id:         Long    = 0,
        @ColumnInfo(name = "dt")            var dt:         Long    = 0,
        @ColumnInfo(name = "city")          var city:       String  = "",
        @ColumnInfo(name = "country")       var country:    String  = "",
        @ColumnInfo(name = "temp_day")      var tempDay:    Double  = 0.0,
        @ColumnInfo(name = "temp_night")    var tempNight:  Double  = 0.0,
        @ColumnInfo(name = "icon")          var icon:       String  = ""
) {
    constructor(): this(0, 0, "", "", 0.0, 0.0, "")
}