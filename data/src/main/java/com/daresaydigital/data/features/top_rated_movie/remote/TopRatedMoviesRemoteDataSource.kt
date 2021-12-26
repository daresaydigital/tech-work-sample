package com.daresaydigital.data.features.top_rated_movie.remote

import com.daresaydigital.core.utils.GlobalDispatcher
import com.daresaydigital.data.model.MovieDTO
import com.daresaydigital.data.util.ApiResult
import com.daresaydigital.data.util.callAwait
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopRatedMoviesRemoteDataSource @Inject constructor(
    private val getTopRatedApiService: TopRatedMoviesApiService,
    private val globalDispatcher: GlobalDispatcher
) {

    /**
     * It is better to have thread select here because each data provider knows
     * better that which thread is suitable for this data
     * and also it prevent issues in other layers.
     */

    suspend fun getTopRatedMovies(page:Int): ApiResult<List<MovieDTO>> {
        return withContext(globalDispatcher.io) {
            getTopRatedApiService.getTopRatedMovies(page).callAwait { response ->
                response
            }
        }
    }
}