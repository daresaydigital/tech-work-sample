package com.suroid.weatherapp.utils

import com.suroid.weatherapp.models.remote.ResponseStatus
import io.reactivex.subjects.PublishSubject

/**
 * Extension function to push a failed event with an exception to the observing responseStatus
 * */
fun <T> PublishSubject<ResponseStatus<T>>.failed(e: Throwable, tag: Any) {
    with(this) {
        loading(false, tag)
        onNext(ResponseStatus.failure(e, tag))
    }
}

/**
 * Extension function to push  a success event with data to the observing responseStatus
 * */
fun <T> PublishSubject<ResponseStatus<T>>.success(t: T, tag: Any) {
    with(this) {
        loading(false, tag)
        onNext(ResponseStatus.success(t, tag))
    }
}

/**
 * Extension function to push the loading status to the observing responseStatus
 * */
fun <T> PublishSubject<ResponseStatus<T>>.loading(isLoading: Boolean, tag: Any) {
    this.onNext(ResponseStatus.loading(isLoading, tag))
}