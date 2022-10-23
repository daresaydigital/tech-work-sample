package com.github.hramogin.movieapp.presentation.screens.moviesList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.hramogin.movieapp.domain.model.Movie
import com.github.hramogin.movieapp.domain.useCases.movie.GetMoviesUseCase
import com.github.hramogin.movieapp.presentation.base.viewModel.ActionLiveData
import com.github.hramogin.movieapp.presentation.base.viewModel.BaseViewModel
import com.github.hramogin.movieapp.presentation.base.viewModel.Event

class MoviesListViewModel(private val getMoviesUseCase: GetMoviesUseCase) : BaseViewModel() {

    private val _openDetailsAction: ActionLiveData<Event<String>> = ActionLiveData()
    private val _moviesLiveData: MutableLiveData<List<Movie>> = MutableLiveData()

    val openDetailsScreenAction: LiveData<Event<String>> = _openDetailsAction
    val moviesLiveData: LiveData<List<Movie>> = _moviesLiveData

    override fun onCreate() {
        super.onCreate()
        loadMovies()
    }

    private fun loadMovies() {
        getMoviesUseCase.invoke(viewModelScope, GetMoviesUseCase.Params()) {
            it.either(::onHandleError, ::onFilmsLoaded)
        }
    }

    private fun onFilmsLoaded(movies: List<Movie>) {
        _moviesLiveData.value = movies
    }
}