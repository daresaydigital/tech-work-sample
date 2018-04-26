package com.vp.weatherapp.data.local.entity

import android.arch.persistence.room.Embedded


data class CityWithForecast(
        @Embedded
        var cityEntity:  CityEntity,

        var temp:        Double? = 0.0,
        var description: String? = "",
        var icon:        String? = ""
)
