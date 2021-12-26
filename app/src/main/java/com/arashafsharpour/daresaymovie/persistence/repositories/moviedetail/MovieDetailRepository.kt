package com.arashafsharpour.daresaymovie.persistence.repositories.moviedetail

import com.arashafsharpour.daresaymovie.infrastructure.models.contracts.Credits
import com.arashafsharpour.daresaymovie.infrastructure.models.contracts.MovieDetail
import com.arashafsharpour.daresaymovie.infrastructure.models.contracts.MovieImages
import com.arashafsharpour.daresaymovie.infrastructure.models.contracts.Review
import com.arashafsharpour.daresaymovie.infrastructure.network.ApiClientBuilder
import javax.inject.Inject

class MovieDetailRepository @Inject constructor(
    private val movieDetailApi: IMovieDetailApi
): IMovieDetailRepository {

    override fun getMovieDetail(movieId: String): ApiClientBuilder<MovieDetail> {
        return ApiClientBuilder { movieDetailApi.getPopularMovies(movieId) }
    }

    override fun getCredits(movieId: String): ApiClientBuilder<Credits> {
        return ApiClientBuilder { movieDetailApi.getCredit(movieId) }
    }

    override fun getReviews(movieId: String, page: Int): ApiClientBuilder<Review> {
        return ApiClientBuilder { movieDetailApi.getReviews(movieId, page) }
    }

    override fun getImages(movieId: String): ApiClientBuilder<MovieImages> {
        return ApiClientBuilder { movieDetailApi.getImages(movieId) }
    }
}