package ir.sass.domain.repository

import ir.sass.basedomain.model.Domain
import ir.sass.domain.model.DiscoverMovieModel
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun discoverMovies() : Flow<Domain<DiscoverMovieModel>>
}