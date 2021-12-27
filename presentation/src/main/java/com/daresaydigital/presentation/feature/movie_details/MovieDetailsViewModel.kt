package com.daresaydigital.presentation.feature.movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.daresaydigital.core.utils.GlobalDispatcher
import com.daresaydigital.domain.features.movie_details.model.MovieDetails
import com.daresaydigital.domain.features.movie_details.usecase.GetMovieDetailsUseCase
import com.daresaydigital.domain.features.movie_details.usecase.MovieDetailsParams
import com.daresaydigital.domain.model.Movie
import com.daresaydigital.domain.model.Result
import com.daresaydigital.presentation.base.BaseViewModel
import com.daresaydigital.presentation.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val globalDispatcher: GlobalDispatcher
): BaseViewModel(){

    private val _progressVisibilityLiveData = MutableLiveData<Boolean>()
    val progressVisibilityLiveData: LiveData<Boolean> = _progressVisibilityLiveData

    private val _failureEventLiveData = SingleLiveEvent<String?>()
    val failureEventLiveData: LiveData<String?> = _failureEventLiveData

    private val _movieListLiveData = MutableLiveData<MovieDetails>()
    val movieListLiveData: LiveData<MovieDetails> = _movieListLiveData

    @InternalCoroutinesApi
    fun getMovieDetails(id:Int){
        viewModelScope.launch(globalDispatcher.main) {
            setPageLoading(true)

            getMovieDetailsUseCase
                .executeStream(MovieDetailsParams(id))
                .collect { result ->
                    setPageLoading(false)
                    when(result){
                        is Result.Success -> {
                            handleDataSucceed(result.value)
                        }
                        is Result.Failure -> {
                            handleDataFailure(result.error)
                        }

                    }
                }
        }
    }

    private fun setPageLoading(isLoading: Boolean) {
        _progressVisibilityLiveData.value = isLoading
    }

    private fun handleDataFailure(error: String) {
        _failureEventLiveData.value = error
    }

    private fun handleDataSucceed(movie: MovieDetails) {
        _movieListLiveData.value = movie
    }
}