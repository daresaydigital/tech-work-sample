package com.arashafsharpour.daresaymovie.persistence.repositories.profile

import com.arashafsharpour.daresaymovie.infrastructure.models.MovieCategoryType
import com.arashafsharpour.daresaymovie.infrastructure.models.contracts.Account
import com.arashafsharpour.daresaymovie.infrastructure.models.contracts.Movies
import com.arashafsharpour.daresaymovie.infrastructure.network.ApiClientBuilder

interface IProfileRepository {
    fun getProfile(apiKey: String): ApiClientBuilder<Account>
    fun getMoviesByType(categoryType: MovieCategoryType, page: Int) : ApiClientBuilder<Movies>
}