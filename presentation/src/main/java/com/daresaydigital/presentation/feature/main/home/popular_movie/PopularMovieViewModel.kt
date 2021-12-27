package com.daresaydigital.presentation.feature.main.home.popular_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.daresaydigital.core.utils.GlobalDispatcher
import com.daresaydigital.domain.features.popular_movie.usecase.GetPopularMoviesParams
import com.daresaydigital.domain.features.popular_movie.usecase.GetPopularMoviesUseCase
import com.daresaydigital.domain.model.Movie
import com.daresaydigital.domain.model.Result
import com.daresaydigital.presentation.base.BaseViewModel
import com.daresaydigital.presentation.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularMovieViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val globalDispatcher: GlobalDispatcher
) : BaseViewModel(){

    private val _progressVisibilityLiveData = MutableLiveData<Boolean>()
    val progressVisibilityLiveData: LiveData<Boolean> = _progressVisibilityLiveData

    private val _failureEventLiveData = SingleLiveEvent<Pair<String?,Int>>()
    val failureEventLiveData: LiveData<Pair<String?,Int>> = _failureEventLiveData

    private val _movieListLiveData = MutableLiveData<List<Movie>?>()
    val movieListLiveData: LiveData<List<Movie>?> = _movieListLiveData

    private var currentPageNumber = 1
    private var totalPageNumber = 1

    fun getPopularPage(isRefresh:Boolean = true){

        if (isRefresh) {
            currentPageNumber = 1
            totalPageNumber = 1
        } else {
            currentPageNumber ++
        }

        viewModelScope.launch(globalDispatcher.main) {
            setPageLoading(true)
            if (currentPageNumber == 1 || currentPageNumber < totalPageNumber){
                when(
                    val result = getPopularMoviesUseCase.execute(GetPopularMoviesParams(currentPageNumber))
                ){
                    is Result.Success -> {
                        totalPageNumber = result.value.totalPages
                        currentPageNumber = result.value.page
                        handleDataSucceed(result.value.results)
                    }
                    is Result.Failure -> {
                        handleDataFailure(result.error)
                    }
                }
            }
            setPageLoading(false)
        }
    }

    private fun setPageLoading(isLoading: Boolean) {
        _progressVisibilityLiveData.value = isLoading
    }

    private fun handleDataFailure(error: String) {
        _failureEventLiveData.value = Pair(error,currentPageNumber)
    }

    private fun handleDataSucceed(movies: List<Movie>) {
        _movieListLiveData.value = movies
    }
}