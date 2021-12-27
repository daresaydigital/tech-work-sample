package com.mousavi.hashem.common

sealed class Either<out T, out E> {
    data class Success<T, E>(val data: T) : Either<T, E>()

    data class Error<T, E>(val error: E) : Either<T, E>()
}