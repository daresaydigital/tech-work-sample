package com.arashafsharpour.daresaymovie.infrastructure.models.contracts

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

@Keep
data class Review(
    @SerializedName("id")
    var id: Int,
    @SerializedName("page")
    var page: Int,
    @SerializedName("results")
    var results: ArrayList<ReviewResults>,
    @SerializedName("total_pages")
    var totalPages: Int,
    @SerializedName("total_results")
    var totalResults: Int
)
