package com.github.hramogin.movieapp.data.model

import com.google.gson.annotations.SerializedName

class MovieApi(
    @SerializedName("id")
    val id: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("title")
    val title: String,
)