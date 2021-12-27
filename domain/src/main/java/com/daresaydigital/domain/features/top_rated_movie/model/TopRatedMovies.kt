package com.daresaydigital.domain.features.top_rated_movie.model

import com.daresaydigital.domain.model.Movie


data class TopRatedMovies (
    val page:Int,
    val results:List<Movie>,
    val totalPages:Int,
    val totalResults:Int
    )