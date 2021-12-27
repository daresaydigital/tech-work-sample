package com.daresaydigital.presentation.feature.movie_details

import com.daresaydigital.core.utils.GlobalDispatcher
import com.daresaydigital.domain.features.movie_details.usecase.GetMovieDetailsUseCase
import com.daresaydigital.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val globalDispatcher: GlobalDispatcher
): BaseViewModel(){


}