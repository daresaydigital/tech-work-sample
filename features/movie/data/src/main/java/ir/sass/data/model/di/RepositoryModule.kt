package ir.sass.data.model.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.sass.data.model.repository.MovieRepositoryImp
import ir.sass.domain.repository.MovieRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindMovieRepository(movieRepositoryImp: MovieRepositoryImp) : MovieRepository
}