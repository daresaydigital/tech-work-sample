package ir.sass.movie.ui.fragments.mainList

import app.cash.turbine.test
import ir.sass.domain.usecase.DiscoverMovieUseCase
import ir.sass.movie.ui.base.BaseViewModelTest
import ir.sass.movie.ui.di.fakeRepoSuccess
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Test

class MovieListFragmentViewModelTest : BaseViewModelTest() {

    @ExperimentalCoroutinesApi
    @Test
    fun `check if calling getMovies() method would get data from usecase`() = testScope.runTest{
        val useCase = DiscoverMovieUseCase(fakeRepoSuccess())
        val viewModel = MovieListFragmentViewModel(useCase)
        viewModel.getMovies()
        viewModel.movies.test {
            delay(1)
            assert(expectMostRecentItem()!!.results!![0].title == "fake title")
        }
    }
}