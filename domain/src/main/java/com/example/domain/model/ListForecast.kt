package com.example.domain.model




open class ListForecast {

    var dt: Long? = null

    var main: Main? = null

    var weather: List<Weather>? = null

    var clouds: Clouds? = null

    var wind: Wind? = null

    var sys: Sys? = null

    var dtTxt: String? = null

}