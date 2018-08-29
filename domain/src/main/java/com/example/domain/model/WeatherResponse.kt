package com.example.domain.model

class WeatherResponse {
    var id: String? = null

    var dt: String? = null

    var clouds: Clouds? = null

    var coord: Coord? = null

    var wind: Wind? = null

    var cod: String? = null

    var visibility: String? = null

    var sys: Sys? = null

    var name: String? = null

    var base: String? = null

    var weather: Array<Weather>? = null

    var main: Main? = null

    override fun toString(): String {
        return "ClassPojo [id = $id, dt = $dt, clouds = $clouds, coord = $coord, wind = $wind, cod = $cod, visibility = $visibility, sys = $sys, name = $name, base = $base, weather = $weather, main = $main]"
    }
}