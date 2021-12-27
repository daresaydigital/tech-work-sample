package com.mousavi.hashem.mymoviesapp.data.repository


import com.google.common.truth.Truth.assertThat
import com.mousavi.hashem.common.Either
import com.mousavi.hashem.mymoviesapp.data.local.MovieDao
import com.mousavi.hashem.mymoviesapp.data.remote.Api
import com.mousavi.hashem.mymoviesapp.data.remote.NetworkDataSource
import com.mousavi.hashem.mymoviesapp.data.remote.dto.MovieDto
import com.mousavi.hashem.mymoviesapp.data.remote.dto.PageDataDto
import com.mousavi.hashem.mymoviesapp.domain.model.Movie
import com.mousavi.hashem.mymoviesapp.domain.model.PageData
import com.mousavi.hashem.mymoviesapp.domain.repository.MoviesRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class MoviesRepositoryImplTest {

    private lateinit var networkDataSource: NetworkDataSource
    private lateinit var dao: MovieDao
    private lateinit var repository: MoviesRepository


    @Before
    fun setUp() {
        networkDataSource = Mockito.mock(NetworkDataSource::class.java)
        dao = Mockito.mock(MovieDao::class.java)
        repository = MoviesRepositoryImpl(networkDataSource, dao)
    }

    @Test
    fun `test get popular movies success`(): Unit = runBlocking {
        `when`(networkDataSource.getPopularMovies("en-US", 1))
            .thenReturn(Either.Success(PageDataDto(page = 1,
                getMovieDtoList(),
                totalResults = 100,
                totalPages = 3)))

        val popularMovies = repository.getPopularMovies("en-US", 1)
        assertThat(popularMovies).isInstanceOf(Either.Success::class.java)
        val success: Either.Success<PageData, String> = popularMovies as Either.Success
        val pageData: PageData = success.data
        assertThat(pageData.movies).hasSize(1)
        val movie = Movie(
            backdropPath = Api.IMAGE_BASE_URL + "backdropPath",
            genreIds = listOf(1, 2),
            id = 20,
            overview = "fun movie",
            posterPath = Api.IMAGE_BASE_URL + "posterPath",
            releaseDate = "2020-02-14",
            title = "Title",
            voteAverage = 5.5,
            voteCount = 1001
        )

        assertThat(pageData.movies[0]).isEqualTo(movie)
    }

    private fun getMovieDtoList(): List<MovieDto> {
        return listOf(
            MovieDto(
                adult = false,
                backdropPath = "backdropPath",
                genreIds = listOf(1, 2),
                id = 20,
                originalLanguage = "en",
                originalTitle = "Test Movie",
                overview = "fun movie",
                popularity = 2.4,
                posterPath = "posterPath",
                releaseDate = "2020-02-14",
                title = "Title",
                video = false,
                voteAverage = 5.5,
                voteCount = 1001
            )
        )
    }
}

