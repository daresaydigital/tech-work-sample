package com.arashafsharpour.daresaymovie.infrastructure.models.contracts

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ProductionCompanies(
    @SerializedName("name")
    var name: String,
    @SerializedName("id")
    var id: Int,
    @SerializedName("logo_path")
    var logoPath: String?,
    @SerializedName("origin_country")
    var originCountry: String
)
