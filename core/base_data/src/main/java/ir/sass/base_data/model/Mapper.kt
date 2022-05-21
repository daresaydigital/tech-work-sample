package ir.sass.base_data.model

import com.google.gson.Gson
import java.lang.Exception

fun <T>toJsonString(input : T) : String = Gson().toJson(input).toString()

inline fun <reified T>toReal(input : String) : T?{
    return try {
        Gson().fromJson(input,T::class.java)
    }catch (e : Exception){
        null
    }
}

