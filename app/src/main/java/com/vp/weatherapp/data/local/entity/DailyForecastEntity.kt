package com.vp.weatherapp.data.local.entity

import android.arch.persistence.room.*
import android.arch.persistence.room.ForeignKey.CASCADE


@Entity(tableName = "daily_forecast",
        indices = [Index("city_id")],
        foreignKeys = [ForeignKey(
                entity = CityEntity::class,
                parentColumns = ["city_id"],
                childColumns = ["city_id"],
                onDelete = CASCADE)])
data class DailyForecastEntity @Ignore constructor(
        @PrimaryKey(autoGenerate = true)    var id:         Long    = 0,
        @ColumnInfo(name = "city_id")       var cityId:     Long    = 0,
        @ColumnInfo(name = "dt")            var dt:         Long    = 0,
        @ColumnInfo(name = "temp_day")      var tempDay:    Double  = 0.0,
        @ColumnInfo(name = "temp_night")    var tempNight:  Double  = 0.0,
        @ColumnInfo(name = "icon")          var icon:       String  = "",
        @ColumnInfo(name = "description")   var description:String  = ""
) {
    constructor(): this(0,0, 0, 0.0, 0.0, "", "")
}