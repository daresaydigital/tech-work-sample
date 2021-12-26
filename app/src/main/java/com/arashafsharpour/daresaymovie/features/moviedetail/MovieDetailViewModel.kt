package com.arashafsharpour.daresaymovie.features.moviedetail

import androidx.lifecycle.MutableLiveData
import com.arashafsharpour.daresaymovie.features.moviedetail.infrastracture.IMovieDetailCoordinator
import com.arashafsharpour.daresaymovie.infrastructure.exceptions.exceptionHandling.ExceptionHandler
import com.arashafsharpour.daresaymovie.infrastructure.extensions.launch
import com.arashafsharpour.daresaymovie.infrastructure.models.contracts.*
import com.arashafsharpour.daresaymovie.infrastructure.platform.BaseViewModel
import com.arashafsharpour.daresaymovie.persistence.repositories.moviedetail.IMovieDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel
@Inject constructor(
    val coordinator: IMovieDetailCoordinator,
    private val movieDetailRepository: IMovieDetailRepository,
    override val exceptionHandler: ExceptionHandler
) : BaseViewModel(coordinator) {

    var movieDetail = MutableLiveData<MovieDetail>()
    var credits = MutableLiveData<Credits>()
    var director = MutableLiveData("")
    var review = MutableLiveData<Review>()
    var reviewPreview = MutableLiveData<ReviewResults>()
    var movieImages = MutableLiveData<MovieImages>()

    fun getMovieDetail(id: String) = launch {
            movieDetailRepository.getMovieDetail(id)
                .onSuccess { movieDetail.postValue(it) }
                .onError { exceptionHandler.handle(it, coordinator) }
                .call()
    }

    fun getCredits(id: String) = launch {
        movieDetailRepository.getCredits(id)
            .onSuccess {
                credits.postValue(it)
                findDirector(it)
            }
            .onError { exceptionHandler.handle(it, coordinator) }
            .call()
    }

    private fun findDirector(it: Credits) {
        it.crew.forEach() { crew ->
            if (crew.job == "Director") director.postValue(crew.name)
        }
    }

    fun getReviews(id: String) = launch {
        movieDetailRepository.getReviews(id)
            .onSuccess {
                review.postValue(it)
                if (!it.results.isNullOrEmpty() && it.results.size > 0) {
                    reviewPreview.postValue(it.results[0])
                }
            }
            .onError { exceptionHandler.handle(it, coordinator) }
            .call()
    }

    fun getPosters(id: String) = launch {
        movieDetailRepository.getImages(id)
            .onSuccess { movieImages.postValue(it) }
            .onError { exceptionHandler.handle(it, coordinator) }
            .call()
    }


}