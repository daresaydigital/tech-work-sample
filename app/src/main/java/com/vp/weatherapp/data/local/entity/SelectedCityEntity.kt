package com.vp.weatherapp.data.local.entity

import android.arch.persistence.room.*
import android.arch.persistence.room.ForeignKey.CASCADE


@Entity(tableName = "selected_city",
        indices = [Index("city_id")],
        foreignKeys = [ForeignKey(
                entity = CityEntity::class,
                parentColumns = ["city_id"],
                childColumns = ["city_id"],
                onDelete = CASCADE)])
data class SelectedCityEntity @Ignore constructor(
        @PrimaryKey
        @ColumnInfo(name = "city_id")       var cityId: Long = 0
) {
    constructor(): this(0)
}