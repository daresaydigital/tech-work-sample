package com.arashafsharpour.daresaymovie.infrastructure.models.contracts

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ProductionCountries(
    @SerializedName("iso_3166_1")
    var iso: String,
    @SerializedName("name")
    var name: String
)
