package com.daresay.movies.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.daresay.movies.data.api.TmdbRepository
import com.daresay.movies.data.models.favorites.FavoriteWithMovieDetails
import com.daresay.movies.data.models.movie.MovieList
import com.daresay.movies.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieFavoritesViewModel @Inject constructor(private val repository: TmdbRepository) : ViewModel() {
    fun getAllFavorites() : LiveData<Resource<List<FavoriteWithMovieDetails>>> {
        return repository.getAllFavorites()
    }
}