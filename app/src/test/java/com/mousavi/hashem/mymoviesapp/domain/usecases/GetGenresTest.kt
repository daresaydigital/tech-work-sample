package com.mousavi.hashem.mymoviesapp.domain.usecases


import com.mousavi.hashem.mymoviesapp.domain.repository.MoviesRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class GetGenresTest {

    private lateinit var getGenres: GetGenresUseCase
    private lateinit var moviesRepository: MoviesRepository

    @Before
    fun setUp() {
        moviesRepository = Mockito.mock(MoviesRepository::class.java)
        getGenres = GetGenres(moviesRepository)
    }

    @Test
    fun `test getGenre must call movie repository`(): Unit = runBlocking {
        getGenres()
        verify(moviesRepository, times(1)).getGenres()
    }

}