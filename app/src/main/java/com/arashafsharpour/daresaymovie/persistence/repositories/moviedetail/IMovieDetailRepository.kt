package com.arashafsharpour.daresaymovie.persistence.repositories.moviedetail

import com.arashafsharpour.daresaymovie.infrastructure.models.contracts.Credits
import com.arashafsharpour.daresaymovie.infrastructure.models.contracts.MovieDetail
import com.arashafsharpour.daresaymovie.infrastructure.models.contracts.MovieImages
import com.arashafsharpour.daresaymovie.infrastructure.models.contracts.Review
import com.arashafsharpour.daresaymovie.infrastructure.network.ApiClientBuilder

interface IMovieDetailRepository {
    fun getMovieDetail(movieId: String) : ApiClientBuilder<MovieDetail>
    fun getCredits(movieId: String) : ApiClientBuilder<Credits>
    fun getReviews(movieId: String, page: Int = 1) : ApiClientBuilder<Review>
    fun getImages(movieId: String) : ApiClientBuilder<MovieImages>
}