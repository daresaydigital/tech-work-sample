package com.daresaydigital.domain.features.popular_movie.model

import com.daresaydigital.common.model.Movie


data class PopularMovies (
    val page:Int,
    val results:List<Movie>,
    val totalPages:Int,
    val totalResults:Int
    )