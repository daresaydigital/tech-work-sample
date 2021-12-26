package com.arashafsharpour.daresaymovie.infrastructure.models.contracts

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Account(
    @SerializedName("avatar")
    var avatar: Avatar,
    @SerializedName("id")
    var id: Int,
    @SerializedName("iso_639_1")
    var iso639: String,
    @SerializedName("iso_3166_1")
    var iso316: String,
    @SerializedName("include_adult")
    var includeAdult: Boolean,
    @SerializedName("username")
    var username: String,
    @SerializedName("name")
    var name: String
)
