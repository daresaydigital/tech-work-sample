package com.example.domain.model

class City {
    var coord: Coord? = null

    var id: String? = null

    var name: String? = null

    var country: String? = null

    override fun toString(): String {
        return "ClassPojo [coord = $coord, id = $id, name = $name, country = $country]"
    }
}