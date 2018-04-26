package com.vp.weatherapp.data.local.fts

//import com.dslplatform.json.CompiledJson
import com.google.gson.annotations.SerializedName

//dsl-json
//@CompiledJson
//data class CityJson(
//        val id:         Long,
//        val name:       String,
//        val country:    String,
//        val coord:      CoordJson
//)
//
//@CompiledJson
//data class CoordJson(
//        val lat: Double,
//        val lon: Double
//)

// Gson
data class CityGson(
        @SerializedName("id")       val id:         Long,
        @SerializedName("name")     val name:       String,
        @SerializedName("country")  val country:    String,
        @SerializedName("coord")    val coord:      CoordGson
)

data class CoordGson(
        @SerializedName("lat") val lat: Double,
        @SerializedName("lon") val lon: Double
)