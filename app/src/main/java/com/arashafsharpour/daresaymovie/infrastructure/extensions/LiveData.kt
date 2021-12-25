package com.arashafsharpour.daresaymovie.infrastructure.extensions

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.notifyObserver() {
    this.value = value
}