package com.daresaydigital.domain.features.popular_movie.model

import com.daresaydigital.domain.model.MovieDomain


data class PopularMoviesDomain (
    val page:Int,
    val results:List<MovieDomain>,
    val totalPages:Int,
    val totalResults:Int
    )