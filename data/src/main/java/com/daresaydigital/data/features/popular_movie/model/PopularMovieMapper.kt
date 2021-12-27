package com.daresaydigital.data.features.popular_movie.model

import com.daresaydigital.data.model.toDomainArrayModel
import com.daresaydigital.domain.features.popular_movie.model.PopularMovies
import com.daresaydigital.domain.model.Movie


fun PopularMoviesDTO.toPopularMoviesDomain(): PopularMovies {
    return PopularMovies(this.page,this.results.toDomainArrayModel(),this.total_pages,this.total_results)
}