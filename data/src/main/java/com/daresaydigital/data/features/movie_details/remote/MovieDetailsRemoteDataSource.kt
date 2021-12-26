package com.daresaydigital.data.features.movie_details.remote

import com.daresaydigital.core.utils.GlobalDispatcher
import com.daresaydigital.data.features.movie_details.model.MovieDetailsDTO
import com.daresaydigital.data.util.ApiResult
import com.daresaydigital.data.util.callAwait
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDetailsRemoteDataSource @Inject constructor(
    private val getMovieDetailsApiService: MovieDetailsApiService,
    private val globalDispatcher: GlobalDispatcher
) {

    /**
     * It is better to have thread select here because each data provider knows
     * better that which thread is suitable for this data
     * and also it prevent issues in other layers.
     */

    suspend fun getMovieDetails(id:Int): ApiResult<MovieDetailsDTO> {
        return withContext(globalDispatcher.io) {
            getMovieDetailsApiService.getMovieDetails(id).callAwait { response ->
                response
            }
        }
    }
}