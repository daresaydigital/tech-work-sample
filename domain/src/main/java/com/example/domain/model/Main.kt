package com.example.domain.model

class Main {
    var humidity: String? = null

    var pressure: String? = null

    var temp_max: String? = null

    var temp_min: String? = null

    var temp: String? = null

    override fun toString(): String {
        return "ClassPojo [humidity = $humidity, pressure = $pressure, temp_max = $temp_max, temp_min = $temp_min, temp = $temp]"
    }
}