package com.daresay.movies.data.models.authentication

data class SessionToken(
    val session_id: String,
    val success: Boolean
)