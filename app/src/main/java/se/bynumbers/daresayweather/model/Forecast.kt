package se.bynumbers.daresayweather.model
import kotlinx.serialization.*

@Serializable
class Main(
    var sea_level: Double = 0.0,
    var grnd_level : Double = 0.0,
    var temp_kf : Double = 0.0,
    var temp : Double = 0.0,
    var feels_like : Double = 0.0,
    var temp_min : Double = 0.0,
    var temp_max : Double = 0.0,
    var pressure : Int = 0,
    var humidity : Int = 0

)


class Rain (
   @SerialName("3h")
   var threeh : Double = 0.0
)

class Snow
{
    @SerialName("3h")
    var threeh = 0.0
}

class List(
    var dt : Int = 0,
    var main : Main? = null,
    var weather : List? = null,
    var clouds : Clouds? = null,
    var wind :  Wind? = null,
    var sys : Sys? = null,
    var dt_txt : String? = null,
    var rain : Rain? = null,
    var snow : Snow? = null
)



data class City(
    var id: Int = 0,
    var name : String? = null,
    var coord : Coord? = null,
    var country : String? = null
)

class Forecast(
    var cod : String? = null,
    var message : Double = 0.0,
    var cnt : Int = 0,
    var list : List,
    var city: City? = null
)