package com.arashafsharpour.daresaymovie.infrastructure.models.contracts

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AuthorDetails(
    @SerializedName("name")
    var name: String,
    @SerializedName("username")
    var username: String,
    @SerializedName("avatar_path")
    var avatarPath: String?,
    @SerializedName("rating")
    var rating: Int?
)
