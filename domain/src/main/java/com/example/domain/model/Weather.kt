package com.example.domain.model

class Weather {
    var id: String? = null

    var icon: String? = null

    var description: String? = null

    var main: String? = null

    override fun toString(): String {
        return "ClassPojo [id = $id, icon = $icon, description = $description, main = $main]"
    }
}