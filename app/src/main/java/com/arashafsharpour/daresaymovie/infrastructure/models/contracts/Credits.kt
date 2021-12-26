package com.arashafsharpour.daresaymovie.infrastructure.models.contracts

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

@Keep
data class Credits(
    @SerializedName("id")
    var id: Int,
    @SerializedName("cast")
    var cast: ArrayList<Cast>,
    @SerializedName("crew")
    var crew: ArrayList<Crew>
)
