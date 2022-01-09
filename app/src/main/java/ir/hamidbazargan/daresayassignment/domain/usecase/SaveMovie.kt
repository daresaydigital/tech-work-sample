package ir.hamidbazargan.daresayassignment.domain.usecase

import ir.hamidbazargan.daresayassignment.domain.entity.MovieEntity
import ir.hamidbazargan.daresayassignment.domain.repository.Repository

class SaveMovie(
    private val repository: Repository
) : UseCase<MovieEntity> {
    override suspend fun execute(movieEntity: MovieEntity) =
        repository.saveMovie(movieEntity)
}