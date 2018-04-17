package com.vp.weatherapp.data.local.entity

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.Relation


data class CityWithForecast(
        @Embedded
        var cityEntity: CityEntity,

        var temp: Double = 0.0
)
//{
//    constructor(): this(null, 0)
//}
