package com.mousavi.hashem.mymoviesapp.presentaion.explore.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mousavi.hashem.mymoviesapp.domain.model.Movie
import com.mousavi.hashem.mymoviesapp.domain.usecases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val saveMovieToDatabaseUseCaseImpl: SaveFavoriteMovieToDatabaseUseCase,
    private val deleteFavoriteMovieUseCaseImpl: DeleteFavoriteMovieUseCase,
    private val checkIfFavoriteUseCaseImpl: CheckIfFavoriteUseCase,
) : ViewModel() {

    private var _ifFavorite = MutableStateFlow(false)
    val ifFavorite = _ifFavorite.asStateFlow()

    fun saveAsFavorite(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            saveMovieToDatabaseUseCaseImpl.invoke(movie)
            checkIsFavorite(movie)
        }
    }

    fun deleteFavorite(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteFavoriteMovieUseCaseImpl.invoke(movie)
            checkIsFavorite(movie)
        }
    }

    fun checkIsFavorite(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            _ifFavorite.value = checkIfFavoriteUseCaseImpl(movie)
        }
    }
}