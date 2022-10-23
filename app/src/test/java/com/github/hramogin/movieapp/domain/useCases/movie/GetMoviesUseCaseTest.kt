package com.github.hramogin.movieapp.domain.useCases.movie

import com.github.hramogin.movieapp.data.repository.movie.MovieRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verifyBlocking
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
internal class GetMoviesUseCaseTest {


    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }


    @Test
    fun `check fetch data from server and store in db`() = runBlocking {
        with(createEnvironment()) {
            getMoviesUseCase.invoke(this@runBlocking, mock(), mock())

            verifyBlocking(repository) { getFilmsFromServer() }
            verifyBlocking(repository) { setFilmsToDb(any()) }
            verifyBlocking(repository, never()) { getFilmsFromDb() }
        }
    }

    private class Environment(
        val getMoviesUseCase: GetMoviesUseCase,
        val repository: MovieRepository,
    )

    private fun createEnvironment(): Environment {
        val repository: MovieRepository = mock {
            onBlocking { getFilmsFromServer() } doReturn emptyList()
            onBlocking { setFilmsToDb(any()) } doReturn Unit
            onBlocking { getFilmsFromDb() } doReturn emptyList()
        }
        return Environment(
            getMoviesUseCase = GetMoviesUseCase(repository),
            repository = repository,
        )
    }
}