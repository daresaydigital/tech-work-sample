package com.daresaydigital.data.features.popular_movie.remote

import com.daresaydigital.common.utils.GlobalDispatcher
import com.daresaydigital.data.features.popular_movie.model.PopularMoviesDTO
import com.daresaydigital.data.util.ApiResult
import com.daresaydigital.data.util.callAwait
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PopularMoviesRemoteDataSource @Inject constructor(
    private val getPopularMovieApiService: PopularMovieApiService,
    private val globalDispatcher: GlobalDispatcher
) {

    /**
     * It is better to have thread select here because each data provider knows
     * better that which thread is suitable for this data
     * and also it prevent issues in other layers.
     */

    suspend fun getPopularMovies(page:Int): ApiResult<PopularMoviesDTO> {
        return withContext(globalDispatcher.io) {
            getPopularMovieApiService.getPopularMovies(page).callAwait { response ->
                response
            }
        }
    }
}