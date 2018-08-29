package com.example.domain.model

class Sys {
    var message: String? = null

    var id: String? = null

    var sunset: String? = null

    var sunrise: String? = null

    var type: String? = null

    var country: String? = null

    override fun toString(): String {
        return "ClassPojo [message = $message, id = $id, sunset = $sunset, sunrise = $sunrise, type = $type, country = $country]"
    }
}