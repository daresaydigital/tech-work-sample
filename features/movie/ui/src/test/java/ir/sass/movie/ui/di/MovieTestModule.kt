package ir.sass.movie.ui.di

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import ir.sass.basedomain.model.Domain
import ir.sass.domain.model.DiscoverMovieModel
import ir.sass.domain.model.ResultModel
import ir.sass.domain.repository.MovieRepository
import ir.sass.domain.usecase.DiscoverMovieUseCase
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
        DiscoverMovieUseCase(fakeRepoSuccess())
}

fun fakeRepoSuccess() = object : MovieRepository {

    override fun discoverMovies(): Flow<Domain<DiscoverMovieModel>> = flow {
        emit(
            Domain.Data(
                Result.success(
                    DiscoverMovieModel(
                        1,
                        listOf(
                            ResultModel(
                                true,"fake path", listOf(),1,
                                "En","fake title","fake overview",1.0,
                                "fake path","fake date","fake title",true,
                                1.0,1
                            )
                        )
                        ,1,1,200,null,true)
                    )
                )
            )
    }
}