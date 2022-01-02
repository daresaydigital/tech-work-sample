package com.daresaydigital.common.utils

/**
 *  constants for network, we can use gradle fields
 *  instead when we want to have different urls for movies
 *  and development
 */
object NetworkConstants {

    const val api_key = "638714c562c6bdba5ca9cc1299816e09"

    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val POPULAR_ENDPOINT = "movie/popular"
    const val TOP_RATED_ENDPOINT = "movie/top_rated"
    const val MOVIE_DETAIL_ENDPOINT = "movie/{id}"
    const val MOVIE_REVIEW_ENDPOINT = "movie/{id}/reviews"

    const val BASE_URL_IMAGE_W500 = "https://image.tmdb.org/t/p/w500"
    const val BASE_URL_IMAGE_ORIGINAL = "https://image.tmdb.org/t/p/original"


}