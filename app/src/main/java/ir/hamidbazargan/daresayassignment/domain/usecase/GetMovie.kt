package ir.hamidbazargan.daresayassignment.domain.usecase

import ir.hamidbazargan.daresayassignment.domain.entity.MovieEntity
import ir.hamidbazargan.daresayassignment.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetMovie(
    private val repository: Repository
) : UseCaseWithFlow<Int> {
    override suspend fun execute(id: Int): Flow<List<MovieEntity>> =
        repository.getMovie(id).map {
            listOf(it)
        }
}