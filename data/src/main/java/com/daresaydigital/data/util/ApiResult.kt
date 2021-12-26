package com.daresaydigital.data.util

/**
 * network result class that represent success or failure of
 * app requests
 */
sealed class ApiResult<out V> {

    data class Success<V>(val value: V) : ApiResult<V>()
    data class Failure(val error: ErrorModel) : ApiResult<Nothing>()
}

data class ErrorModel(
    val message: String?
)