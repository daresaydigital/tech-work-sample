package com.arashafsharpour.daresaymovie.persistence.repositories.movies

import com.arashafsharpour.daresaymovie.infrastructure.models.MovieCategoryType
import com.arashafsharpour.daresaymovie.infrastructure.models.contracts.Movies
import com.arashafsharpour.daresaymovie.infrastructure.network.ApiClientBuilder

interface IMoviesRepository {
    fun getMoviesByType(categoryType: MovieCategoryType, page: Int) : ApiClientBuilder<Movies>
}