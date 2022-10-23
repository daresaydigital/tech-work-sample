package com.github.hramogin.movieapp.presentation.screens.moviesList

import com.github.hramogin.movieapp.domain.useCases.movie.GetMoviesUseCase
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verifyBlocking
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito

internal class MoviesListViewModelTest {

    @Test
    fun `check view model call getMoviesUseCase during create`() = runBlocking {
        with(createEnvironment()) {
            viewModel.onCreate()

            verifyBlocking(getMoviesUseCase) { invoke(any(), any(), any()) }
        }
    }

    private class Environment(
        val viewModel: MoviesListViewModel,
        val getMoviesUseCase: GetMoviesUseCase
    )

    private fun createEnvironment(): Environment {
        val getMoviesUseCase: GetMoviesUseCase = mock(defaultAnswer = Mockito.RETURNS_DEEP_STUBS) {
            on { invoke(any(), any(), any()) } doReturn mock()
        }
        return Environment(
            MoviesListViewModel(getMoviesUseCase),
            getMoviesUseCase = getMoviesUseCase,
        )
    }
}