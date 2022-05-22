package ir.sass.base_data.model

import com.google.gson.Gson
import java.lang.Exception

/*this methods is used when we want to navigate between fragments
the reason that we need these methods is that we can not use parcelable
in our domain layers, using serialize is not a good option too
so we cast the data to a json and re-cast it again in our destination
we can use these methods for other things too*/

fun <T>toJsonString(input : T) : String = Gson().toJson(input).toString()

inline fun <reified T>toReal(input : String) : T?{
    return try {
        Gson().fromJson(input,T::class.java)
    }catch (e : Exception){
        null
    }
}

