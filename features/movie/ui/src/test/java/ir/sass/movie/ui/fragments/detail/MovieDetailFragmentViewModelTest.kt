package ir.sass.movie.ui.fragments.detail

import app.cash.turbine.test
import ir.sass.domain.model.ResultModel
import ir.sass.domain.usecase.CheckIfAMovieIsInDataBaseUseCase
import ir.sass.domain.usecase.DeleteMovieFromLocalUseCase
import ir.sass.domain.usecase.SaveMovieToLocalUseCase
import ir.sass.movie.ui.base.BaseViewModelTest
import ir.sass.movie.ui.di.fakeRepoSuccess
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Test

class MovieDetailFragmentViewModelTest : BaseViewModelTest() {

    @ExperimentalCoroutinesApi
    @Test
    fun `check if call checkItemExists then _bookmarked must emit true, at first it must be false`() = testScope.runTest{
        val viewModel = provideViewModel()
        viewModel.checkItemExists()
        var helper = "First"
        viewModel.bookmarked.test {
            val response = this.awaitItem()
            if(helper == "First"){
                helper = "Not First"
                assert(!response)
            }
            else
                assert(response)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `check if call saveOrDelete  movie then _message must emit Saved ! after that if call that again the message must be Deleted !`() = testScope.runTest{
        val viewModel = provideViewModel()
        delay(5)
        viewModel.saveOrDelete()
        var helper = "First"
        viewModel.message.test {
            val response = this.awaitItem()
            if(helper == "First"){
                assert(response == "Saved !")
                helper = "Not First"
                viewModel.saveOrDelete()
            }else
                assert(response == "Deleted !")
        }
    }


    private fun provideViewModel() : MovieDetailFragmentViewModel{
        val saveMovieToLocalUseCase: SaveMovieToLocalUseCase =
            SaveMovieToLocalUseCase(fakeRepoSuccess())
        val deleteMovieFromLocalUseCase: DeleteMovieFromLocalUseCase =
            DeleteMovieFromLocalUseCase(fakeRepoSuccess())
        val checkIfAMovieIsInDataBaseUseCase : CheckIfAMovieIsInDataBaseUseCase =
            CheckIfAMovieIsInDataBaseUseCase(fakeRepoSuccess())
        return MovieDetailFragmentViewModel(saveMovieToLocalUseCase,
            deleteMovieFromLocalUseCase,checkIfAMovieIsInDataBaseUseCase).apply {
                resultModel = ResultModel(
                    true,"fake path", listOf(),1,"En","fake title",
                    "fake overview",1.0,"fake path",
                    "fake date","fake title",false,1.0,1
                )
        }
    }

}