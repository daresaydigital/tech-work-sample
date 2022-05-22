package ir.sass.movie.ui.fragments.detail

import dagger.hilt.android.lifecycle.HiltViewModel
import ir.sass.base_ui.MotherViewModel
import ir.sass.domain.model.ResultModel
import ir.sass.domain.usecase.DeleteMovieFromLocalUseCase
import ir.sass.domain.usecase.SaveMovieToLocalUseCase
import javax.inject.Inject

@HiltViewModel
class MovieDetailFragmentViewModel @Inject constructor(
    private val saveMovieToLocalUseCase : SaveMovieToLocalUseCase,
    private val deleteMovieFromLocalUseCase : DeleteMovieFromLocalUseCase,
    ): MotherViewModel() {

    fun saveToLocal(input : ResultModel){
        saveMovieToLocalUseCase(input)
    }

    fun deleteFromLocal(input : ResultModel){
        deleteMovieFromLocalUseCase(input)
    }

}