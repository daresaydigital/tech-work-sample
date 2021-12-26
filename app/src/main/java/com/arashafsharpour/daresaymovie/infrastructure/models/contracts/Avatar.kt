package com.arashafsharpour.daresaymovie.infrastructure.models.contracts

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Avatar(
    @SerializedName("gravatar")
    var gravatar: Gravatar
)
