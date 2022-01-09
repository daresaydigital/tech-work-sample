package ir.hamidbazargan.daresayassignment.domain.usecase

import ir.hamidbazargan.daresayassignment.domain.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

interface UseCaseWithFlow<P> {
    suspend fun execute(param: P): Flow<List<MovieEntity>>
}