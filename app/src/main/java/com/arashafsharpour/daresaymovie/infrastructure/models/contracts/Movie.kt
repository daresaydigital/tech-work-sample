package com.arashafsharpour.daresaymovie.infrastructure.models.contracts

import androidx.annotation.Keep
import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName

@Keep
data class Movie(
    @SerializedName("poster_path")
    var posterPath: String?,
    @SerializedName("adult")
    var adult: Boolean,
    @SerializedName("overview")
    var overview: String,
    @SerializedName("release_date")
    var releaseDate: String,
    @SerializedName("genre_ids")
    var genreIds: ArrayList<Int>,
    @SerializedName("id")
    var id: Int,
    @SerializedName("original_title")
    var originalTitle: String,
    @SerializedName("original_language")
    var originalLanguage: String,
    @SerializedName("title")
    var title: String,
    @SerializedName("backdrop_path")
    var backdropPath: String?,
    @SerializedName("popularity")
    var popularity: Float,
    @SerializedName("vote_count")
    var voteCount: Int,
    @SerializedName("video")
    var video: Boolean,
    @SerializedName("vote_average")
    var voteAverage: Float
) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(
                oldItem: Movie,
                newItem: Movie
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Movie,
                newItem: Movie
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    }
}
