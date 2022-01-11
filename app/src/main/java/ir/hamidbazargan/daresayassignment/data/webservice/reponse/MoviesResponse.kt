package ir.hamidbazargan.daresayassignment.data.webservice.reponse

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<MovieResponse>,
    @SerializedName("total_pages") val totalPage: Int,
    @SerializedName("total_results") val totalResults: Int
)

data class MovieResponse(
    @SerializedName("id") var id: Int,
    @SerializedName("title") var title: String,
    @SerializedName("original_title") var originalTitle: String,
    @SerializedName("overview") var overview: String,
    @SerializedName("original_language") var originalLanguage: String,
    @SerializedName("release_date") var releaseDate: String?,
    @SerializedName("adult") var adult: Boolean,
    @SerializedName("backdrop_path") var backdropPath: String?,
    @SerializedName("genre_ids") var genreIds: List<Int>,
    @SerializedName("popularity") var popularity: Float,
    @SerializedName("poster_path") var posterPath: String?,
    @SerializedName("video") var video: Boolean,
    @SerializedName("vote_average") var voteAverage: Float,
    @SerializedName("vote_count") var voteCount: Int
)
