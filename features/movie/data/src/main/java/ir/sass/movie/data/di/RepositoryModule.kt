package ir.sass.movie.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.sass.domain.repository.MovieRepository
import ir.sass.movie.data.repository.MovieRepositoryImp

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindMovieRepository(movieRepositoryImp: MovieRepositoryImp): MovieRepository
}