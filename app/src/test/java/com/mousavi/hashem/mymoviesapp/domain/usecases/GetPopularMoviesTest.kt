package com.mousavi.hashem.mymoviesapp.domain.usecases


import com.google.common.truth.Truth.assertThat
import com.mousavi.hashem.common.Either
import com.mousavi.hashem.mymoviesapp.domain.model.Genre
import com.mousavi.hashem.mymoviesapp.domain.model.Genres
import com.mousavi.hashem.mymoviesapp.domain.model.Movie
import com.mousavi.hashem.mymoviesapp.domain.model.PageData
import com.mousavi.hashem.mymoviesapp.domain.repository.MoviesRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class GetPopularMoviesTest {

    private lateinit var getPopularMovies: GetPopularMovies
    private lateinit var moviesRepository: MoviesRepository
    private lateinit var getGenresUseCase: GetGenresUseCase


    @Before
    fun setUp() {
        moviesRepository = Mockito.mock(MoviesRepository::class.java)
        getGenresUseCase = Mockito.mock(GetGenresUseCase::class.java)
        getPopularMovies = GetPopularMovies(moviesRepository, getGenresUseCase)
    }

    @Test
    fun test(): Unit = runBlocking {
        `when`(getGenresUseCase()).thenReturn(Either.Success(Genres(listOf(Genre(1, "action")))))
        `when`(moviesRepository.getPopularMovies("en-US", 1))
            .thenReturn(
                Either.Success(
                    PageData(page = 1,
                        movies = arrayListOf(
                            Movie(id = 1, genreIds = listOf(1)),
                            Movie(id = 2, genreIds = listOf(2))
                        )
                    )
                )
            )

        val popularMovies: Either<PageData, String> = getPopularMovies("en-US", 1)
        assertThat(popularMovies).isInstanceOf(Either.Success::class.java)
        val pageData: PageData = (popularMovies as Either.Success).data
        assertThat(pageData.movies).hasSize(2)
        assertThat(pageData.movies).contains(Movie(id = 1, genreIds = listOf(1), genreNames = mutableListOf("action")))
        assertThat(pageData.movies).contains(Movie(id = 2, genreIds = listOf(2), genreNames =  arrayListOf()))
    }

}