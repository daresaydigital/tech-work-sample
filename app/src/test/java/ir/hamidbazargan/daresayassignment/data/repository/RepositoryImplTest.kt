package ir.hamidbazargan.daresayassignment.data.repository

import io.mockk.*
import ir.hamidbazargan.daresayassignment.data.db.MovieDao
import ir.hamidbazargan.daresayassignment.data.db.entity.MovieDataBaseEntity
import ir.hamidbazargan.daresayassignment.data.mapper.toMovieEntity
import ir.hamidbazargan.daresayassignment.data.webservice.WebServiceApi
import ir.hamidbazargan.daresayassignment.data.webservice.reponse.MovieResponse
import ir.hamidbazargan.daresayassignment.data.webservice.reponse.MoviesResponse
import ir.hamidbazargan.daresayassignment.domain.entity.MovieEntity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class RepositoryImplTest {
    private val apiService = mockk<WebServiceApi>()
    private val movieDao = mockk<MovieDao>()
    private val repository = RepositoryImpl(apiService, movieDao)

    private val movieResponse = MovieResponse(
        id = 3,
        title = "test3",
        originalLanguage = "test3",
        originalTitle = "test3",
        overview = "test3",
        adult = false,
        genreIds = listOf(),
        popularity = 0.1F,
        video = false,
        voteAverage = 0.1F,
        voteCount = 1,
        releaseDate = null,
        backdropPath = null,
        posterPath = null
    )

    private val moviesResponse = MoviesResponse(
        page = 1,
        results = listOf(movieResponse),
        totalPage = 1,
        totalResults = 1
    )

    private val movieDataBaseEntity = MovieDataBaseEntity(
        id = 1,
        title = "test1",
        originalLanguage = "test1",
        originalTitle = "test1",
        overview = "test1",
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
    fun getPopularMovies() {
        runBlockingTest {
            coEvery { apiService.getPopularMovies(1) } returns moviesResponse
            var response: List<MovieEntity>? = null
            repository.getPopularMovies(1).collect {
                response = it
            }

            assert(response?.size == 1)

            coVerify(exactly = 1) {
                apiService.getPopularMovies(1)
            }
        }
    }

    @Test
    fun getTopRatedMovies() {
        runBlockingTest {
            coEvery { apiService.getTopRatedMovies(1) } returns moviesResponse
            var response: List<MovieEntity>? = null
            repository.getTopRatedMovies(1).collect {
                response = it
            }

            assert(response?.size == 1)

            coVerify(exactly = 1) {
                apiService.getTopRatedMovies(1)
            }
        }
    }

    @Test
    fun getBookmarkMovies() {
        runBlockingTest {
            coEvery { movieDao.queryMovies() } returns listOf(movieDataBaseEntity)
            var response: List<MovieEntity>? = null
            repository.getBookmarkMovies(1).collect {
                response = it
            }

            assert(response?.size == 1)

            coVerify(exactly = 1) {
                movieDao.queryMovies()
            }
        }
    }

    @Test
    fun getMovieFromDataBase() {
        runBlockingTest {
            coEvery { movieDao.queryMovie(1) } returns movieDataBaseEntity
            var response: MovieEntity? = null
            repository.getMovie(1).collect {
                response = it
            }

            assert(response != null)
            assert(response?.id == 1)

            coVerify(exactly = 1) {
                movieDao.queryMovie(1)
            }
        }
    }

    @Test
    fun getMovieFromWeb() {
        runBlockingTest {
            coEvery { movieDao.queryMovie(3) } returns null
            coEvery { apiService.getMovie(3) } returns movieResponse
            var response: MovieEntity? = null

            repository.getMovie(3).collect {
                response = it
            }

            assert(response != null)
            assert(response?.id == 3)

            coVerify(exactly = 1) {
                apiService.getMovie(3)
            }
        }
    }

    @Test
    fun saveMovie() {
        runBlockingTest {
            coEvery { movieDao.insertMovie(any()) } just runs

            repository.saveMovie(movieResponse.toMovieEntity())

            coVerify(exactly = 1) {
                movieDao.insertMovie(any())
            }
        }
    }
}