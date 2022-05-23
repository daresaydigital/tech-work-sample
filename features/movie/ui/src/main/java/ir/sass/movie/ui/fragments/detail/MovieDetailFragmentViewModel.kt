package ir.sass.movie.ui.fragments.detail

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.sass.base_ui.MotherViewModel
import ir.sass.domain.model.ResultModel
import ir.sass.domain.usecase.CheckIfAMovieIsInDataBaseUseCase
import ir.sass.domain.usecase.DeleteMovieFromLocalUseCase
import ir.sass.domain.usecase.SaveMovieToLocalUseCase
import ir.sass.shared_domain.MovieListType
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailFragmentViewModel @Inject constructor(
    private val saveMovieToLocalUseCase: SaveMovieToLocalUseCase,
    private val deleteMovieFromLocalUseCase: DeleteMovieFromLocalUseCase,
    private val checkIfAMovieIsInDataBaseUseCase : CheckIfAMovieIsInDataBaseUseCase
) : MotherViewModel() {

    lateinit var resultModel: ResultModel
    lateinit var type : MovieListType

    private val _message: MutableSharedFlow<String> = MutableSharedFlow()
    val message: SharedFlow<String> = _message

    private val _bookmarked: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val bookmarked: StateFlow<Boolean> = _bookmarked

    fun checkItemExists(){
        action(checkIfAMovieIsInDataBaseUseCase(resultModel),false){
            viewModelScope.launch {
                _bookmarked.emit(it)
            }
        }
    }

    private fun saveToLocal(input: ResultModel) {
        saveMovieToLocalUseCase(input)
    }

    private fun deleteFromLocal(input: ResultModel) {
        deleteMovieFromLocalUseCase(input)
    }

    fun saveOrDelete() {
        val message = if (_bookmarked.value) {
            deleteFromLocal(resultModel)
            "Deleted !"
        } else {
            saveToLocal(resultModel)
            "Saved !"
        }

        viewModelScope.launch {
            _message.emit(message)
            _bookmarked.emit(!_bookmarked.value)
        }
    }


}