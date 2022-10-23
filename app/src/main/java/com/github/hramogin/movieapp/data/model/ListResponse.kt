package com.github.hramogin.movieapp.data.model

import com.google.gson.annotations.SerializedName

class ListResponse<T>(
    @SerializedName("page")
    val page: String,
    @SerializedName("results")
    val results: List<T>,
)