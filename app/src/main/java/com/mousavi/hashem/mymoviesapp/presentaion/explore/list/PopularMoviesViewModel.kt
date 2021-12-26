package com.mousavi.hashem.mymoviesapp.presentaion.explore.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mousavi.hashem.common.Either
import com.mousavi.hashem.mymoviesapp.domain.model.PageData
import com.mousavi.hashem.mymoviesapp.domain.usecases.GetPopularMoviesUseCase
import com.mousavi.hashem.mymoviesapp.domain.usecases.GetPopularMoviesUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    private val getPopularMoviesUseCaseImplUseCase: GetPopularMoviesUseCase,
) : ViewModel() {

    private val _popularMovies = MutableStateFlow(PageData())
    val popularMovies = _popularMovies.asStateFlow()

    private val _popularMoviesError = MutableStateFlow(false)
    val popularMoviesError = _popularMoviesError.asStateFlow()

    private val _popularMoviesLoading = MutableStateFlow(false)
    val popularMoviesLoading = _popularMoviesLoading.asStateFlow()

    fun getPopularMovies(
        language: String = "en-US",
        page: Int = 1,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _popularMoviesError.emit(false)
            _popularMoviesLoading.emit(true)
            when (val result = getPopularMoviesUseCaseImplUseCase(language, page)) {
                is Either.Success -> {
                    _popularMoviesLoading.emit(false)
                    _popularMovies.value = result.data
                }
                is Either.Error -> {
                    _popularMoviesLoading.emit(false)
                    _popularMoviesError.emit(true)
                }
            }

        }
    }
}