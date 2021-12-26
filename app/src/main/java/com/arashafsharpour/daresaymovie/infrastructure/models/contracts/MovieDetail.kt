package com.arashafsharpour.daresaymovie.infrastructure.models.contracts

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

@Keep
data class MovieDetail(
    @SerializedName("adult")
    var adult: Boolean,
    @SerializedName("backdrop_path")
    var backdropPath: String?,
    @SerializedName("belongs_to_collection")
    var belongsToCollection: BelongsToCollection?,
    @SerializedName("budget")
    var budget: Long,
    @SerializedName("genres")
    var genres: ArrayList<Genres>,
    @SerializedName("homepage")
    var homePage: String,
    @SerializedName("id")
    var id: Int,
    @SerializedName("imdb_id")
    var imdbId: String?,
    @SerializedName("original_language")
    var originalLanguage: String,
    @SerializedName("original_title")
    var originalTitle: String,
    @SerializedName("overview")
    var overView: String,
    @SerializedName("popularity")
    var popularity: Float,
    @SerializedName("poster_path")
    var posterPath: String?,
    @SerializedName("production_companies")
    var productionCompanies: ArrayList<ProductionCompanies>,
    @SerializedName("production_countries")
    var productionCountries: ArrayList<ProductionCountries>,
    @SerializedName("release_date")
    var releaseDate: String,
    @SerializedName("revenue")
    var revenue: Long,
    @SerializedName("runtime")
    var runtime: Int?,
    @SerializedName("spoken_languages")
    var spokenLanguages: ArrayList<SpokenLanguages>,
    @SerializedName("status")
    var status: String,
    @SerializedName("tagline")
    var tagline: String?,
    @SerializedName("title")
    var title: String,
    @SerializedName("video")
    var video: String,
    @SerializedName("vote_average")
    var voteAverage: Float,
    @SerializedName("vote_count")
    var voteCount: Int
)
