package com.daresay.movies.data.models.authentication

data class RequestToken(
    val expires_at: String,
    val request_token: String,
    val success: Boolean
)