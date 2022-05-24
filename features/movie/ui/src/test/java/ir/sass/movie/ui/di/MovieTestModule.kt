package ir.sass.movie.ui.di

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import ir.sass.base_domain.model.Domain
import ir.sass.domain.model.DiscoverMovieModel
import ir.sass.domain.model.ResultModel
import ir.sass.domain.repository.MovieRepository
import ir.sass.domain.usecase.DiscoverPopularMovieUseCase
import ir.sass.domain.usecase.DiscoverMyFavoriteMoviesOfflineUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [MovieModule::class]
)
class MovieTestModule {
    @Provides
    fun provideSearchAmongSongUseCase() =
        DiscoverPopularMovieUseCase(fakeRepoSuccess())

    @Provides
    fun provideDiscoverMyFavoriteMoviesOfflineUseCase() =
        DiscoverMyFavoriteMoviesOfflineUseCase(fakeRepoSuccess())
}

fun fakeRepoSuccess() = object : MovieRepository {



    override fun discoverPopularMovies(page: Int): Flow<Domain<DiscoverMovieModel>> = flow {
        emit(
            Domain.Data(
                Result.success(
                    DiscoverMovieModel(
                        1,
                        listOf(
                            ResultModel(
                                true, "fake popular path", listOf(), 1,
                                "En", "fake popular title", "fake popular overview", 1.0,
                                "fake popular path", "fake popular path", "fake popular date", true,1.0, 1
                            )
                        ), 1, 1, 200, null, true
                    )
                )
            )
        )
    }

    override fun discoverTopMovies(page: Int): Flow<Domain<DiscoverMovieModel>> = flow {
        emit(
            Domain.Data(
                Result.success(
                    DiscoverMovieModel(
                        1,
                        listOf(
                            ResultModel(
                                true, "fake top path", listOf(), 1,
                                "En", "fake top title", "fake top overview", 1.0,
                                "fake top path", "fake path", "fake top date", true,1.0, 1
                            )
                        ), 1, 1, 200, null, true
                    )
                )
            )
        )
    }

    override fun discoverMoviesFromLocal(): Flow<Domain<DiscoverMovieModel>> = flow {
        emit(
            Domain.Data(
                Result.success(
                    DiscoverMovieModel(
                        1,
                        listOf(
                            ResultModel(
                                true,
                                "fake db path",
                                listOf(),
                                1,
                                "En",
                                "fake db title",
                                "fake db overview",
                                1.0,
                                "fake db path",
                                "fake db path",
                                "fake db date",
                                true,
                                1.0,
                                1
                            )
                        ), 1, 1, 200, null, true
                    )
                )
            )
        )
    }

    override fun saveToLocal(model: ResultModel) {
        // just make sure this method is called
    }

    override fun deleteFromLocal(model: ResultModel) {
        // just make sure this method is called
    }

    override fun isExistOnDb(model: ResultModel): Flow<Domain<Boolean>> = flow {
        emit(Domain.Data(Result.success(true)))
    }
}