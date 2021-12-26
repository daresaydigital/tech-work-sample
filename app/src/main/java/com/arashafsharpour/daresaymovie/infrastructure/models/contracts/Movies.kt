package com.arashafsharpour.daresaymovie.infrastructure.models.contracts

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Movies(
    @SerializedName("page")
    var page: Int,
    @SerializedName("results")
    var result: ArrayList<Movie>,
    @SerializedName("total_pages")
    var totalPages: Int,
    @SerializedName("total_results")
    var totalResults: Int
){


}