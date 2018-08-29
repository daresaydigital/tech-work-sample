package com.example.domain.model

class DailyForecastResponse {
    var message: String? = null

    var cnt: String? = null

    var cod: String? = null

    var list: List<ListItemForecastDaily>? = null

    var city: City? = null

    override fun toString(): String {
        return "ClassPojo [message = $message, cnt = $cnt, cod = $cod, list = $list, city = $city]"
    }
}