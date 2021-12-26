package com.arashafsharpour.daresaymovie.persistence.repositories.profile

import com.arashafsharpour.daresaymovie.infrastructure.models.MovieCategoryType
import com.arashafsharpour.daresaymovie.infrastructure.models.contracts.Account
import com.arashafsharpour.daresaymovie.infrastructure.models.contracts.Movies
import com.arashafsharpour.daresaymovie.infrastructure.network.ApiClientBuilder
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val profileApi: IProfileApi
) : IProfileRepository {
    override fun getProfile(apiKey: String): ApiClientBuilder<Account> {
        return ApiClientBuilder { profileApi.getProfile(apiKey) }
    }

    override fun getMoviesByType(
        categoryType: MovieCategoryType,
        page: Int
    ): ApiClientBuilder<Movies> {
        return when (categoryType) {
            MovieCategoryType.WATCHLATER -> {
                ApiClientBuilder { profileApi.getWatchLaterMovies(page) }
            }
            else -> {
                return ApiClientBuilder { profileApi.getFavoriteMovies(page) }
            }
        }
    }
}