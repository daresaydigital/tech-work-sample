package com.vp.weatherapp.data.remote

import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type


// Because the openweathermap API sucks
object EnvelopeConverterFactory : Converter.Factory() {

    override fun responseBodyConverter(
            type: Type?,
            annotations: Array<Annotation>?,
            retrofit: Retrofit?): Converter<ResponseBody, *>? {


        val envelopeType = TypeToken.getParameterized(Envelope::class.java, type).type
        val delegate = retrofit!!.nextResponseBodyConverter<Envelope>(this, envelopeType, annotations!!)
        return EnvelopeConverter(delegate)
    }
}

class EnvelopeConverter(val delegate: Converter<ResponseBody, Envelope>)
    : Converter<ResponseBody, Any> {

    override fun convert(responseBody: ResponseBody): Any? {
        val envelope = delegate.convert(responseBody)
        return when (envelope.cod) {
            200 -> null // let gson handle it
            else -> throw Envelope.FailureException(envelope.cod)
        }
    }
}

class Envelope {
    var cod: Int = 0
    var message: String? = null

    internal class FailureException(errorCode: Int) : RuntimeException("Error code: $errorCode")
}


