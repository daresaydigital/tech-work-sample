package ir.sass.movie.ui.fragments.mainList

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import ir.sass.domain.usecase.DiscoverPopularMovieUseCase
import ir.sass.domain.usecase.DiscoverMyFavoriteMoviesOfflineUseCase
import ir.sass.domain.usecase.DiscoverTopMovieUseCase
import ir.sass.movie.ui.base.BaseViewModelTest
import ir.sass.movie.ui.di.fakeRepoSuccess
import ir.sass.shared_domain.MovieListType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Test

class MovieListFragmentViewModelTest : BaseViewModelTest() {

    @ExperimentalCoroutinesApi
    @Test
    fun `in online popular mode check if calling getMovies() method would get data from usecase`() =
        testScope.runTest {
            val discoverPopularMovieUseCase = DiscoverPopularMovieUseCase(fakeRepoSuccess())
            val discoverTopMovieUseCase : DiscoverTopMovieUseCase = mock()
            val discoverMyFavoriteMoviesOfflineUseCase : DiscoverMyFavoriteMoviesOfflineUseCase = mock()
            val viewModel = MovieListFragmentViewModel(discoverPopularMovieUseCase,
                discoverTopMovieUseCase,discoverMyFavoriteMoviesOfflineUseCase)
            viewModel.getMovies(MovieListType.POPULAR)
            viewModel.movies.test {
                delay(1)
                assert(expectMostRecentItem()!!.results!![0].title == "fake popular title")
            }
        }

    @ExperimentalCoroutinesApi
    @Test
    fun `in online top rated mode check if calling getMovies() method would get data from usecase`() =
        testScope.runTest {
            val discoverPopularMovieUseCase : DiscoverPopularMovieUseCase = mock()
            val discoverTopMovieUseCase = DiscoverTopMovieUseCase(fakeRepoSuccess())
            val discoverMyFavoriteMoviesOfflineUseCase : DiscoverMyFavoriteMoviesOfflineUseCase = mock()
            val viewModel = MovieListFragmentViewModel(discoverPopularMovieUseCase,
                discoverTopMovieUseCase,discoverMyFavoriteMoviesOfflineUseCase)
            viewModel.getMovies(MovieListType.TOP_RATED)
            viewModel.movies.test {
                delay(1)
                assert(expectMostRecentItem()!!.results!![0].title == "fake top title")
            }
        }

    @ExperimentalCoroutinesApi
    @Test
    fun `in offline mode check if calling getMovies() method would get data from usecase`() =
        testScope.runTest {
            val discoverPopularMovieUseCase : DiscoverPopularMovieUseCase = mock()
            val discoverTopMovieUseCase : DiscoverTopMovieUseCase = mock()
            val discoverMyFavoriteMoviesOfflineUseCase = DiscoverMyFavoriteMoviesOfflineUseCase(fakeRepoSuccess())
            val viewModel = MovieListFragmentViewModel(discoverPopularMovieUseCase,
                discoverTopMovieUseCase,discoverMyFavoriteMoviesOfflineUseCase)
            viewModel.getMovies(MovieListType.FAVORITE)
            viewModel.movies.test {
                delay(1)
                assert(expectMostRecentItem()!!.results!![0].title == "fake db title")
            }
        }


}