package com.github.hramogin.movieapp.domain.useCases.base

import com.github.hramogin.movieapp.R


sealed class ApiError(
    val type: String,
    val stringResId: Int = R.string.unknown_error,
    val errorCode: Int = 0
) {

    object UnknownApiError : ApiError(UNKNOWN, R.string.unknown_error, 100)

    companion object {
        const val UNKNOWN = "Unknown error"
    }
}