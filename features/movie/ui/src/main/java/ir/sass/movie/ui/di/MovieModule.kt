package ir.sass.movie.ui.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import ir.sass.domain.repository.MovieRepository
import ir.sass.domain.usecase.DiscoverPopularMovieUseCase

@Module
@InstallIn(FragmentComponent::class)
object MovieModule {
    @Provides
    fun provideDiscoverMovieUseCase(repository: MovieRepository) =
        DiscoverPopularMovieUseCase(repository)
}