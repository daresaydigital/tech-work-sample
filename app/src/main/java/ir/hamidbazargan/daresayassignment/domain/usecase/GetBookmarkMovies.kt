package ir.hamidbazargan.daresayassignment.domain.usecase

import ir.hamidbazargan.daresayassignment.domain.entity.MovieEntity
import ir.hamidbazargan.daresayassignment.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class GetBookmarkMovies(
    private val repository: Repository
) : UseCaseWithFlow<Int> {
    override suspend fun execute(page: Int): Flow<List<MovieEntity>> =
        repository.getBookmarkMovies(page)
}