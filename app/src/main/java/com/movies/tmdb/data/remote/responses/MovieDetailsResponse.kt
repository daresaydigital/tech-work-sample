package com.movies.tmdb.data.remote.responses

import com.google.gson.annotations.SerializedName
import com.movies.tmdb.other.Constants


class MovieDetailsResponse(
  var adult: Boolean = false,
  var id: Int,
  var genres: List<IdAndNameObject>,
  @SerializedName("original_title")
  var originalTitle: String,
  var overview: String,
  @SerializedName("poster_path")
  var posterPath: String,
  @SerializedName("release_date")
  var releaseDate: String,
  var title: String,
  var runtime: Float,
  var status: String,
  @SerializedName("vote_average")
  var voteAverage: Float
) {

    fun getPosterFullImage(): String = Constants.BASE_FOR_FULL_IMAGE.plus(posterPath)

}
