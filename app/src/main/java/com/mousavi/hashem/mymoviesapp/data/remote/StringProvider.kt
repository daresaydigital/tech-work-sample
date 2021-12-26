package com.mousavi.hashem.mymoviesapp.data.remote

interface StringProvider {

    fun getHttpError(): String

    fun getIOExceptionError(): String

    fun getUnknownError(): String
}