package com.daresaydigital.domain.features.top_rated_movie.model

import com.daresaydigital.domain.model.MovieDomain


data class TopRatedMoviesDomain (
    val page:Int,
    val results:List<MovieDomain>,
    val totalPages:Int,
    val totalResults:Int
    )