package com.daresaydigital.presentation.feature.movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.daresaydigital.core.utils.GlobalDispatcher
import com.daresaydigital.domain.features.favourite_movie.usecase.*
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
    private val getFavouriteMovieByIdUseCase: GetFavouriteMovieByIdUseCase,
    private val favouriteMovieByIdUseCase: FavourMovieUseCase,
    private val unFavourMovieUseCase: UnFavourMovieUseCase,
    private val globalDispatcher: GlobalDispatcher
) : BaseViewModel() {

    private val _progressVisibilityLiveData = MutableLiveData<Boolean>()
    val progressVisibilityLiveData: LiveData<Boolean> = _progressVisibilityLiveData

    private val _failureEventLiveData = SingleLiveEvent<String?>()
    val failureEventLiveData: LiveData<String?> = _failureEventLiveData

    private val _movieListLiveData = MutableLiveData<MovieDetails>()
    val movieListLiveData: LiveData<MovieDetails> = _movieListLiveData

    private val _movieIsFavouriteLiveData = MutableLiveData<Boolean>()
    val movieIsFavouriteLiveData: LiveData<Boolean> = _movieIsFavouriteLiveData

    @InternalCoroutinesApi
    fun getMovieDetails(id: Int) {
        viewModelScope.launch(globalDispatcher.main) {
            setPageLoading(true)

            getMovieDetailsUseCase
                .executeStream(MovieDetailsParams(id))
                .collect { result ->
                    setPageLoading(false)
                    when (result) {
                        is Result.Success -> {
                            handleDataSucceed(result.value)
                        }
                        is Result.Failure -> {
                            handleDataFailure(result.error)
                        }

                    }
                }

            val movieFavState =
                getFavouriteMovieByIdUseCase.execute(GetFavouriteMovieByIdParams(id))
            handleMovieFavouriteState(movieFavState is Result.Success && movieFavState.value != null)
        }
    }

    fun changeFavouriteState(movie: Movie) {
        if (progressVisibilityLiveData.value == true) return

        viewModelScope.launch(globalDispatcher.main) {
            if (_movieIsFavouriteLiveData.value == true) {
                unFavourMovieUseCase.execute(UnFavourMovieParams(movie.id))
                handleMovieFavouriteState(false)
            } else {
                favouriteMovieByIdUseCase.execute(FavourMovieParams(movie))
                handleMovieFavouriteState(true)
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

    private fun handleMovieFavouriteState(isFav: Boolean) {
        _movieIsFavouriteLiveData.value = isFav
    }
}