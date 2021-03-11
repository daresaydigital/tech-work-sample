package com.daresay.movies.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.daresay.movies.data.api.TmdbRepository
import com.daresay.movies.data.models.favorites.Favorite
import com.daresay.movies.data.models.moviedetails.MovieDetails
import com.daresay.movies.data.models.moviedetails.Reviews
import com.daresay.movies.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(private val repository: TmdbRepository) : ViewModel() {
    fun getMovieDetails(movieId: Int) : LiveData<Resource<MovieDetails>> {
        return repository.getMovieDetails(movieId = movieId)
    }

    fun getMovieReviews(movieId: Int, page: Int) : LiveData<Resource<Reviews>> {
        return repository.getMovieReviews(movieId, page)
    }

    fun setMovieFavorite(movieId: Int, favorite: Boolean) {
        repository.setMovieFavorite(movieId, favorite)
    }

    fun getFavorite(movieId: Int) : LiveData<Resource<Favorite>> {
        return repository.getFavorite(movieId)
    }
}