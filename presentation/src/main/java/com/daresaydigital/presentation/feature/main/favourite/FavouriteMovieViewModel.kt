package com.daresaydigital.presentation.feature.main.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.daresaydigital.common.utils.GlobalDispatcher
import com.daresaydigital.domain.features.favourite_movie.usecase.GetAllFavourMoviesUseCase
import com.daresaydigital.domain.features.favourite_movie.usecase.UnFavourMovieParams
import com.daresaydigital.domain.features.favourite_movie.usecase.UnFavourMovieUseCase
import com.daresaydigital.common.model.Movie
import com.daresaydigital.domain.model.Result
import com.daresaydigital.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteMovieViewModel @Inject constructor(
    private val getAllFavourMoviesUseCase: GetAllFavourMoviesUseCase,
    private val unFavourMoviesUseCase: UnFavourMovieUseCase,
    private val globalDispatcher: GlobalDispatcher
) : BaseViewModel() {

    private val _favMoviesLiveData = MutableLiveData<List<Movie>?>()
    val favouriteMoviesViewLiveData: LiveData<List<Movie>?> = _favMoviesLiveData

    @InternalCoroutinesApi
    fun getAllFavouriteMovies() {
        viewModelScope.launch(globalDispatcher.main) {
            getAllFavourMoviesUseCase.executeStream().collect {
                if (it is Result.Success) {
                    handleDataSucceed(it.value)
                }
            }
        }
    }

    private fun handleDataSucceed(movies: List<Movie>) {
        _favMoviesLiveData.value = movies
    }

    fun unFavouriteMovie(id: Int) {
        viewModelScope.launch(globalDispatcher.main) {
            unFavourMoviesUseCase.execute(UnFavourMovieParams(id))

            val temp = _favMoviesLiveData.value?.toMutableList()
            temp?.indexOfFirst { it.id == id }?.takeIf { it>=0 }?.let{
                temp.removeAt(it)
                handleDataSucceed(temp)
            }

        }
    }

}