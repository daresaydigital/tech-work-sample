package com.arashafsharpour.daresaymovie.features.profile

import androidx.lifecycle.MutableLiveData
import com.arashafsharpour.daresaymovie.features.profile.infrastracture.IProfileCoordinator
import com.arashafsharpour.daresaymovie.infrastructure.exceptions.exceptionHandling.ExceptionHandler
import com.arashafsharpour.daresaymovie.infrastructure.extensions.launch
import com.arashafsharpour.daresaymovie.infrastructure.extensions.notifyObserver
import com.arashafsharpour.daresaymovie.infrastructure.models.MovieCategoryType
import com.arashafsharpour.daresaymovie.infrastructure.models.contracts.Movie
import com.arashafsharpour.daresaymovie.infrastructure.platform.BaseViewModel
import com.arashafsharpour.daresaymovie.persistence.repositories.profile.IProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileFragmentViewModel
@Inject constructor(
    val coordinator: IProfileCoordinator,
    private val profileRepository: IProfileRepository,
    override val exceptionHandler: ExceptionHandler
    ) : BaseViewModel(coordinator) {

    val favoriteMovies = MutableLiveData(mutableListOf<Movie>())
    val watchLaterMovies = MutableLiveData(mutableListOf<Movie>())

    init {
        if (favoriteMovies.value.isNullOrEmpty()) {
            getFavoriteMovies(categoryType = MovieCategoryType.FAVORITE)
            getWatchLaterMovies(categoryType = MovieCategoryType.WATCHLATER)
        }
    }

    fun getFavoriteMovies(categoryType: MovieCategoryType, page: Int = 1) = launch {
        profileRepository.getMoviesByType(categoryType, page)
            .onSuccess { setMoviesList(it.result, categoryType) }
            .onError { exceptionHandler.handle(it, coordinator) }
            .call()
    }

    fun getWatchLaterMovies(categoryType: MovieCategoryType, page: Int = 1) = launch {
        profileRepository.getMoviesByType(categoryType, page)
            .onSuccess { setMoviesList(it.result, categoryType) }
            .onError { exceptionHandler.handle(it, coordinator) }
            .call()
    }

    private fun setMoviesList(result: ArrayList<Movie>, categoryType: MovieCategoryType) {
        if (!result.isNullOrEmpty())
            when(categoryType) {
                MovieCategoryType.FAVORITE -> {
                    favoriteMovies.value?.addAll(result)
                    favoriteMovies.notifyObserver()
                }
                MovieCategoryType.WATCHLATER -> {
                    watchLaterMovies.value?.addAll(result)
                    watchLaterMovies.notifyObserver()
                }
                else -> {}
            }
    }

}
