package com.example.domain.model


class Temp {
    var min: String? = null

    var eve: String? = null

    var max: String? = null

    var morn: String? = null

    var night: String? = null

    var day: String? = null

    override fun toString(): String {
        return "ClassPojo [min = $min, eve = $eve, max = $max, morn = $morn, night = $night, day = $day]"
    }
}