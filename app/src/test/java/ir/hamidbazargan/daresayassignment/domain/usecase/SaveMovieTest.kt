package ir.hamidbazargan.daresayassignment.domain.usecase

import io.mockk.*
import ir.hamidbazargan.daresayassignment.domain.entity.MovieEntity
import ir.hamidbazargan.daresayassignment.domain.repository.Repository
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class SaveMovieTest {

    private val repository = mockk<Repository>()
    private val useCase = SaveMovie(repository)

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

            coEvery { repository.saveMovie(any()) } just runs

            useCase.execute(movieEntity)

            coVerify(exactly = 1) {
                repository.saveMovie(movieEntity)
            }

        }
    }
}