package com.daresaydigital.domain.model

/**
 * network result class that represent success or failure of
 * app requests
 */
sealed class Result<out V> {

    data class Success<V>(val value: V) : Result<V>()
    data class Failure(val error: String) : Result<Nothing>()
}