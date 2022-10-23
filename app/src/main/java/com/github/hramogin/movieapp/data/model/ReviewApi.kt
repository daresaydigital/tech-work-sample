package com.github.hramogin.movieapp.data.model

import com.google.gson.annotations.SerializedName

class ReviewApi(
    @SerializedName("id")
    val id: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("author")
    val author: String,
)