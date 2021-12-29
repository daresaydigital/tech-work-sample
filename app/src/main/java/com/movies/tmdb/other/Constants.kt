package com.movies.tmdb.other

object Constants {

    const val DATABASE_NAME = "movies_db"

    const val API_KEY = "d9e7589213d1b047222f2774b376c82a"
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val BASE_FOR_FULL_IMAGE = "https://image.tmdb.org/t/p/original"
    const val BASE_FOR_THUMB_IMAGE = "https://image.tmdb.org/t/p/w500"


    const val GRID_SPAN_COUNT = 2


    enum class FilterId(val filterId: Int) {
        MOST_POPULAR(1),
        TOP_RATED(2)
    }

    enum class LanguageCode(val languageCode: String) {
        SWEDEN("sv"),
        ENGLISH("en")
    }
}