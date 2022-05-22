package ir.sass.domain.usecase

import ir.sass.base_domain.model.Domain
import ir.sass.base_domain.usecase.MotherUseCaseWithOnlyOutput
import ir.sass.domain.model.DiscoverMovieModel
import ir.sass.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DiscoverMovieUseCase @Inject constructor(
    private val repository: MovieRepository
) : MotherUseCaseWithOnlyOutput<DiscoverMovieModel>() {
    override fun invoke(): Flow<Domain<DiscoverMovieModel>> = repository.discoverMovies()
}