package ir.hamidbazargan.daresayassignment.domain.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import ir.hamidbazargan.daresayassignment.domain.entity.MovieEntity
import ir.hamidbazargan.daresayassignment.domain.repository.Repository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class GetTopRatedMoviesTest {

    private val repository = mockk<Repository>()
    private val useCase = GetTopRatedMovies(repository)

    private val movieEntity = MovieEntity(
        id = 3,
        title = "test3",
        originalLanguage = "test3",
        originalTitle = "test3",
        overview = "test3",
        adult = false,
        popularity = 0.1F,
        video = false,
        voteAverage = 0.1F,
        voteCount = 1,
        releaseDate = null,
        backdropPath = null,
        posterPath = null
    )

    @Test
    fun execute() {
        runBlockingTest {
            coEvery { repository.getTopRatedMovies(1) } returns flow {
                emit(listOf(movieEntity))
            }

            var responses: List<MovieEntity>? = null
            useCase.execute(1).collect {
                responses = it
            }

            assert(responses?.size == 1)

            coVerify(exactly = 1) {
                repository.getTopRatedMovies(1)
            }

        }
    }
}