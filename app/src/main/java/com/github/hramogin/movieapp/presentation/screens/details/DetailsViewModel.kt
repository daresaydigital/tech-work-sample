package com.github.hramogin.movieapp.presentation.screens.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.hramogin.movieapp.domain.model.MovieDetails
import com.github.hramogin.movieapp.domain.useCases.movie.GetFilmDetailsUseCase
import com.github.hramogin.movieapp.presentation.base.viewModel.BaseViewModel

class DetailsViewModel(
    private val id: String,
    private val getFilmDetailsUseCase: GetFilmDetailsUseCase,
    private val movieDetailsMapper: MovieDetailsMapper,
) : BaseViewModel() {

    private val _filmId: MutableLiveData<String> = MutableLiveData()
    private val _movieDetails: MutableLiveData<MovieDetails> = MutableLiveData()
    private val _detailsItem: MutableLiveData<List<BaseDetailItem>> = MutableLiveData()

    val movieDetails: LiveData<MovieDetails> = _movieDetails
    val detailsItem: LiveData<List<BaseDetailItem>> = _detailsItem

    override fun onCreate() {
        super.onCreate()
        loadFilmDetails()
    }

    private fun loadFilmDetails() {
        getFilmDetailsUseCase.invoke(viewModelScope, GetFilmDetailsUseCase.Param(id)) {
            it.either(::onHandleError, ::onFilmDetailsLoaded)
        }
    }

    private fun onFilmDetailsLoaded(movieDetails: MovieDetails) {
        _filmId.value = movieDetails.id
        _movieDetails.value = movieDetails
        _detailsItem.value = movieDetailsMapper.map(movieDetails)
        return
    }
}