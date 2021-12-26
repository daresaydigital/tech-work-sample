package com.daresaydigital.data.features.popular_movie.model

import com.daresaydigital.data.model.MovieDTO

data class PopularMoviesDTO (
    val page:Int,
    val results:List<MovieDTO>,
    val total_pages:Int,
    val total_results:Int
    )