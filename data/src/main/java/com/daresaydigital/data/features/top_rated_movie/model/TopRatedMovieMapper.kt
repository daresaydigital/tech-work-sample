package com.daresaydigital.data.features.top_rated_movie.model

import com.daresaydigital.data.model.toDomainArrayModel
import com.daresaydigital.domain.features.top_rated_movie.model.TopRatedMovies
import com.daresaydigital.domain.model.Movie

fun TopRatedMoviesDTO.toTopRatedMoviesDomain(): TopRatedMovies {
    return TopRatedMovies(this.page,this.results.toDomainArrayModel(),this.total_pages,this.total_results)
}