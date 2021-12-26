package com.mousavi.hashem.mymoviesapp.presentaion.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mousavi.hashem.mymoviesapp.domain.model.Movie
import com.mousavi.hashem.mymoviesapp.domain.usecases.GetFavoriteMoviesFromDatabaseUseCase
import com.mousavi.hashem.mymoviesapp.domain.usecases.GetFavoriteMoviesFromDatabaseUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val useCase: GetFavoriteMoviesFromDatabaseUseCase,
) : ViewModel() {

    private var _favorites = MutableStateFlow<List<Movie>>(emptyList())
    val favorites = _favorites.asStateFlow()

    init {
        getFavorites()
    }

    private fun getFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = useCase()
            result.onEach {
                _favorites.value = it
            }.launchIn(this)
        }
    }
}