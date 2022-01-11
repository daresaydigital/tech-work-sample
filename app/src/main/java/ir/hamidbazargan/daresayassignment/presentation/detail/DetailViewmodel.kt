package ir.hamidbazargan.daresayassignment.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.hamidbazargan.daresayassignment.domain.entity.MovieEntity
import ir.hamidbazargan.daresayassignment.domain.usecase.UseCase
import ir.hamidbazargan.daresayassignment.domain.usecase.UseCaseWithFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailViewmodel(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val getMovie: UseCaseWithFlow<Int>,
    private val saveMovie: UseCase<MovieEntity>
) : ViewModel() {

    private var _uiModel = MutableLiveData<UiModel>()
    val uiModel: LiveData<UiModel> get() = _uiModel

    fun loadMovie(id: Int) {
        _uiModel.postValue(UiModel.Loading)
        viewModelScope.launch(coroutineDispatcher) {
            try {
                getMovie.execute(id).collect { movies ->
                    _uiModel.postValue(UiModel.Success(movies.first()))
                }
            } catch (e: Exception) {
                _uiModel.postValue(UiModel.Error(e.message.toString()))
            }
        }
    }

    fun saveMovie() {
        if(uiModel.value is UiModel.Success) {
            viewModelScope.launch(coroutineDispatcher) {
                try {
                    saveMovie.execute((uiModel.value as UiModel.Success).movie)
                    _uiModel.postValue(UiModel.Bookmark)
                }catch (e: java.lang.Exception) {
                    _uiModel.postValue(UiModel.Error(e.message.toString()))
                }
            }
        }
    }
}