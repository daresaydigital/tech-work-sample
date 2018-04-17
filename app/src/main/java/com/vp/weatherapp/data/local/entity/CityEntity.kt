package com.vp.weatherapp.data.local.entity

import android.arch.persistence.room.*


@Entity(tableName = "city",
        indices = [Index("city_id", "name")])
data class CityEntity @Ignore constructor(
        @PrimaryKey()
        @ColumnInfo(name = "city_id")   var cityId:     Long    = 0,
        @ColumnInfo(name = "name")      var name:       String  = "",
        @ColumnInfo(name = "country")   var country:    String  = "",
        @ColumnInfo(name = "lat")       var lat:        Double  = 0.0,
        @ColumnInfo(name = "lon")       var lon:        Double  = 0.0
) {
    constructor(): this(0, "", "",0.0, 0.0)
}