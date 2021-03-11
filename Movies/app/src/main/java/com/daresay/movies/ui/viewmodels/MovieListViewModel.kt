package com.daresay.movies.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daresay.movies.data.api.TmdbRepository
import com.daresay.movies.data.models.movie.Movie
import com.daresay.movies.data.models.movie.MovieList
import com.daresay.movies.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(private val repository: TmdbRepository) : ViewModel() {
    fun getTopRatedMovies(page: Int) : LiveData<Resource<MovieList>> {
        return repository.getTopRatedMovies(page = page)
    }

    fun getPopularMovies(page: Int) : LiveData<Resource<MovieList>> {
        return repository.getPopularMovies(page = page)
    }
}