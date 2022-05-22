package ir.sass.movie.ui.fragments.detail

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import ir.sass.domain.model.ResultModel
import ir.sass.domain.repository.MovieRepository
import ir.sass.domain.usecase.DeleteMovieFromLocalUseCase
import ir.sass.domain.usecase.SaveMovieToLocalUseCase
import ir.sass.movie.ui.base.BaseViewModelTest
import ir.sass.movie.ui.di.fakeRepoSuccess
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

class MovieDetailFragmentViewModelTest : BaseViewModelTest() {


    @ExperimentalCoroutinesApi
    @Test
    fun `in online mode make sure when we call saveOrDelete the saveToLocal is called too`() =
        testScope.runTest {
            val repo: MovieRepository = mock()
            val saveMovieToLocalUseCase = SaveMovieToLocalUseCase(repo)
            val deleteMovieFromLocalUseCase = DeleteMovieFromLocalUseCase(repo)
            val viewModel =
                MovieDetailFragmentViewModel(saveMovieToLocalUseCase, deleteMovieFromLocalUseCase)
            viewModel.isFavorite = false
            val resultModel: ResultModel = mock()
            viewModel.resultModel = resultModel
            viewModel.saveOrDelete()
            verify(repo, times(1)).saveToLocal(resultModel)
        }

    @ExperimentalCoroutinesApi
    @Test
    fun `in offline mode make sure when we call saveOrDelete the deleteFromLocal is called too`() =
        testScope.runTest {
            val repo: MovieRepository = mock()
            val saveMovieToLocalUseCase = SaveMovieToLocalUseCase(repo)
            val deleteMovieFromLocalUseCase = DeleteMovieFromLocalUseCase(repo)
            val viewModel =
                MovieDetailFragmentViewModel(saveMovieToLocalUseCase, deleteMovieFromLocalUseCase)
            viewModel.isFavorite = true
            val resultModel: ResultModel = mock()
            viewModel.resultModel = resultModel
            viewModel.saveOrDelete()
            verify(repo, times(1)).deleteFromLocal(resultModel)
        }

    @ExperimentalCoroutinesApi
    @Test
    fun `in online mode make sure when we call saveOrDelete the _active param be false`() =
        testScope.runTest {
            val repo = fakeRepoSuccess()
            val saveMovieToLocalUseCase = SaveMovieToLocalUseCase(repo)
            val deleteMovieFromLocalUseCase = DeleteMovieFromLocalUseCase(repo)
            val viewModel =
                MovieDetailFragmentViewModel(saveMovieToLocalUseCase, deleteMovieFromLocalUseCase)
            viewModel.isFavorite = false
            val resultModel: ResultModel = mock()
            viewModel.resultModel = resultModel

            viewModel.saveOrDelete()
            viewModel.active.test {
                val response = this.awaitItem()
                assert(response)
            }
        }

    @ExperimentalCoroutinesApi
    @Test
    fun `in online mode make sure when we call saveOrDelete the message is emmited with value Saved`() =
        testScope.runTest {
            val repo = fakeRepoSuccess()
            val saveMovieToLocalUseCase = SaveMovieToLocalUseCase(repo)
            val deleteMovieFromLocalUseCase = DeleteMovieFromLocalUseCase(repo)
            val viewModel =
                MovieDetailFragmentViewModel(saveMovieToLocalUseCase, deleteMovieFromLocalUseCase)
            viewModel.isFavorite = false
            val resultModel: ResultModel = mock()
            viewModel.resultModel = resultModel

            viewModel.saveOrDelete()
            viewModel.message.test {
                val response = this.awaitItem()
                assert(response == "Saved !")
            }
        }

    @ExperimentalCoroutinesApi
    @Test
    fun `in offline mode make sure when we call saveOrDelete the _active param be false`() =
        testScope.runTest {
            val repo = fakeRepoSuccess()
            val saveMovieToLocalUseCase = SaveMovieToLocalUseCase(repo)
            val deleteMovieFromLocalUseCase = DeleteMovieFromLocalUseCase(repo)
            val viewModel =
                MovieDetailFragmentViewModel(saveMovieToLocalUseCase, deleteMovieFromLocalUseCase)
            viewModel.isFavorite = true
            val resultModel: ResultModel = mock()
            viewModel.resultModel = resultModel

            viewModel.saveOrDelete()
            viewModel.active.test {
                val response = this.awaitItem()
                assert(response)
            }
        }

    @ExperimentalCoroutinesApi
    @Test
    fun `in offline mode make sure when we call saveOrDeletethe message is emitted with value Deleted`() =
        testScope.runTest {
            val repo = fakeRepoSuccess()
            val saveMovieToLocalUseCase = SaveMovieToLocalUseCase(repo)
            val deleteMovieFromLocalUseCase = DeleteMovieFromLocalUseCase(repo)
            val viewModel =
                MovieDetailFragmentViewModel(saveMovieToLocalUseCase, deleteMovieFromLocalUseCase)
            viewModel.isFavorite = true
            val resultModel: ResultModel = mock()
            viewModel.resultModel = resultModel

            viewModel.saveOrDelete()

            viewModel.message.test {
                val response = this.awaitItem()
                assert(response == "Deleted !")
            }

        }

    @ExperimentalCoroutinesApi
    @Test
    fun `in offline mode make sure when we call saveOrDelete _navigateBack emit true`() =
        testScope.runTest {
            val repo = fakeRepoSuccess()
            val saveMovieToLocalUseCase = SaveMovieToLocalUseCase(repo)
            val deleteMovieFromLocalUseCase = DeleteMovieFromLocalUseCase(repo)
            val viewModel =
                MovieDetailFragmentViewModel(saveMovieToLocalUseCase, deleteMovieFromLocalUseCase)
            viewModel.isFavorite = true
            val resultModel: ResultModel = mock()
            viewModel.resultModel = resultModel

            viewModel.saveOrDelete()
            viewModel.navigateBack.test {
                this.awaitItem()?.let {
                    assert(it)
                }
            }
        }
}