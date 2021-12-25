package com.arashafsharpour.daresaymovie.infrastructure.models.contracts

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Images(
    @SerializedName("base_url")
    var baseurl: String,
    @SerializedName("secure_base_url")
    var secureBaseUrl: String,
    @SerializedName("backdrop_sizes")
    var backdropSizes: ArrayList<String>,
    @SerializedName("logo_sizes")
    var logoSize: ArrayList<String>,
    @SerializedName("poster_size")
    var posterSizes: ArrayList<String>,
    @SerializedName("profile_sizes")
    var profileSizes: ArrayList<String>,
    @SerializedName("still_sizes")
    var stillSizes: ArrayList<String>
) {

}
