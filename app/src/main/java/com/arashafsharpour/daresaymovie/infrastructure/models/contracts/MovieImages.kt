package com.arashafsharpour.daresaymovie.infrastructure.models.contracts

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

@Keep
data class MovieImages(
    @SerializedName("id")
    var id: Int,
    @SerializedName("backdrops")
    var backdrops: ArrayList<BackDrops>,
    @SerializedName("posters")
    var posters: ArrayList<Posters>,
    @SerializedName("logos")
    var logos: ArrayList<Logos>
)
