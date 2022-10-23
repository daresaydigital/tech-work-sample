package com.github.hramogin.movieapp.data.model

import com.google.gson.annotations.SerializedName

class MovieDetailsApi(
    @SerializedName("id")
    val id: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("original_title")
    val title: String,
    @SerializedName("tagline")
    val tagline: String,
    @SerializedName("overview")
    val overview: String,
)