package com.daresaydigital.presentation.feature.popular

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import com.daresaydigital.common.utils.GlobalDispatcher
import com.daresaydigital.domain.features.popular_movie.model.PopularMovies
import com.daresaydigital.domain.features.popular_movie.usecase.GetPopularMoviesUseCase
import com.daresaydigital.common.model.Movie
import com.daresaydigital.presentation.feature.main.home.popular_movie.PopularMovieViewModel
import com.daresaydigital.domain.model.Result


@RunWith(MockitoJUnitRunner::class)
internal class PopularMovieViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var globalDispatcher: GlobalDispatcher

    private val getPopularMoviesUseCase: GetPopularMoviesUseCase = mock()
    private lateinit var viewModel: PopularMovieViewModel

    @Before
    fun setup(){
        globalDispatcher = GlobalDispatcher(
            main = Dispatchers.Unconfined,
            io = Dispatchers.Unconfined,
            default = Dispatchers.Unconfined
        )

        viewModel = PopularMovieViewModel(
            getPopularMoviesUseCase,
            globalDispatcher
        )
    }

    @Test
    fun `should get movies on popular page`() = runBlocking{
        val observer = mock<Observer<Pair<List<Movie>?,Int>>>()
        val argumentCaptor = argumentCaptor<Pair<List<Movie>?,Int>>()

        val fakeItems = PopularMovies(1,listOf(),100,100)

        viewModel.movieListLiveData.observeForever(observer)

        getPopularMoviesUseCase.stub {
            onBlocking { execute(any()) } doReturn Result.Success(
                fakeItems
            )
        }

        viewModel.getPopularPage()

        verify(observer, times(1)).onChanged(argumentCaptor.capture())
    }
}