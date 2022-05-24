package ir.sass.domain.usecase

import ir.sass.base_domain.model.Domain
import ir.sass.base_domain.usecase.MotherUseCase
import ir.sass.domain.model.DiscoverMovieModel
import ir.sass.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DiscoverPopularMovieUseCase @Inject constructor(
    private val repository: MovieRepository
) : MotherUseCase<Int,DiscoverMovieModel>() {
    override fun invoke(page: Int): Flow<Domain<DiscoverMovieModel>> = repository.discoverPopularMovies(page)
}