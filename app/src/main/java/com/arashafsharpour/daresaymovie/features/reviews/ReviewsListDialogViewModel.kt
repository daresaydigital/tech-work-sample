package com.arashafsharpour.daresaymovie.features.reviews

import androidx.lifecycle.MutableLiveData
import com.arashafsharpour.daresaymovie.features.reviews.infrastracture.IReviewsListCoordinator
import com.arashafsharpour.daresaymovie.infrastructure.exceptions.exceptionHandling.ExceptionHandler
import com.arashafsharpour.daresaymovie.infrastructure.extensions.launch
import com.arashafsharpour.daresaymovie.infrastructure.extensions.notifyObserver
import com.arashafsharpour.daresaymovie.infrastructure.models.contracts.ReviewResults
import com.arashafsharpour.daresaymovie.infrastructure.platform.BaseViewModel
import com.arashafsharpour.daresaymovie.persistence.repositories.moviedetail.IMovieDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.ArrayList
import javax.inject.Inject

@HiltViewModel
class ReviewsListDialogViewModel
@Inject constructor(
    val coordinator: IReviewsListCoordinator,
    private val movieDetailRepository: IMovieDetailRepository,
    override val exceptionHandler: ExceptionHandler
) :
    BaseViewModel(coordinator) {

    var review = MutableLiveData(mutableListOf<ReviewResults>())
    var movieName = MutableLiveData<String>()

    fun getReviews(movieId: String, page: Int = 1) = launch {
        movieDetailRepository.getReviews(movieId, page)
            .onSuccess { setReviewValues(it.results) }
            .onError { exceptionHandler.handle(it, coordinator) }
            .call()
    }

    private fun setReviewValues(it: ArrayList<ReviewResults>) {
        if (!it.isNullOrEmpty()) {
            review.value?.addAll(it)
            review.notifyObserver()
        }
    }
}