package ir.sass.movie.ui.fragments.detail

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.sass.base_ui.MotherViewModel
import ir.sass.domain.model.ResultModel
import ir.sass.domain.usecase.DeleteMovieFromLocalUseCase
import ir.sass.domain.usecase.SaveMovieToLocalUseCase
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
) : MotherViewModel() {

    lateinit var resultModel: ResultModel
    var isFavorite = false

    private val _message: MutableSharedFlow<String> = MutableSharedFlow()
    val message: SharedFlow<String> = _message

    private val _active: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val active: StateFlow<Boolean> = _active

    private val _navigateBack: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val navigateBack: StateFlow<Boolean?> = _navigateBack

    private fun saveToLocal(input: ResultModel) {
        saveMovieToLocalUseCase(input)
    }

    private fun deleteFromLocal(input: ResultModel) {
        deleteMovieFromLocalUseCase(input)
    }

    fun saveOrDelete() {
        val message = if (isFavorite) {
            deleteFromLocal(resultModel)
            "Deleted !"
        } else {
            saveToLocal(resultModel)
            "Saved !"
        }

        viewModelScope.launch {
            _message.emit(message)
            _active.emit(false)
            if (isFavorite) {
                _navigateBack.emit(true)
            }
        }
    }


}