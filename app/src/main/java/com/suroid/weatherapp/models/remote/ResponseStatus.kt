package com.suroid.weatherapp.models.remote

/**
 * ResponseStatus is class to represent all the possible states of
 * Api response. It can be passed on to the listeners to notify stats
 */
sealed class ResponseStatus<T>(val tag: Any) {

    data class Progress<T>(var loading: Boolean, val t: Any) : ResponseStatus<T>(t)
    data class Success<T>(var data: T, val t: Any) : ResponseStatus<T>(t)
    data class Failure<T>(val e: Throwable, val t: Any) : ResponseStatus<T>(t)

    companion object {
        fun <T> loading(isLoading: Boolean, tag: Any): ResponseStatus<T> = Progress(isLoading, tag)

        fun <T> success(data: T, tag: Any): ResponseStatus<T> = Success(data, tag)

        fun <T> failure(e: Throwable, tag: Any): ResponseStatus<T> = Failure(e, tag)
    }
}