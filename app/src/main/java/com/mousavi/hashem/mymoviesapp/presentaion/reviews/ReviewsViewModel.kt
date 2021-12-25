package com.mousavi.hashem.mymoviesapp.presentaion.reviews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mousavi.hashem.common.Either
import com.mousavi.hashem.mymoviesapp.domain.model.Reviews
import com.mousavi.hashem.mymoviesapp.domain.usecases.GetReviews
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewsViewModel @Inject constructor(
    private val useCase: GetReviews,
) : ViewModel() {

    private val _reviews = MutableStateFlow(Reviews())
    val reviews = _reviews.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    fun getReviews(
        movieId: Int,
        language: String = "en-US",
        page: Int = 1,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.emit(true)
            when (val result = useCase(movieId, language, page)) {
                is Either.Success -> {
                    _loading.emit(false)
                    _reviews.value = result.data
                }
                is Either.Error -> {
                    _loading.emit(false)
                    //TODO
                }
            }

        }
    }
}