package com.daresaydigital.data.features.top_rated_movie.model

import com.daresaydigital.data.model.MovieDTO

data class TopRatedMoviesDTO (
    val page:Int,
    val results:List<MovieDTO>,
    val total_pages:Int,
    val total_results:Int
    )