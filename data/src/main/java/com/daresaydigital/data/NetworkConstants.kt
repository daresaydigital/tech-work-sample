package com.daresaydigital.data

/**
 *  constants for network, we can use gradle fields
 *  instead when we want to have different urls for movies
 *  and development
 */
object NetworkConstants {

    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val POPULAR_ENDPOINT = "movie/popular"
    const val TOP_RATED_ENDPOINT = "movie/top_rated"
    const val MOVIE_DETAIL_ENDPOINT = "movie/{id}"
    const val MOVIE_REVIEW_ENDPOINT = "movie/{id}/reviews"


}