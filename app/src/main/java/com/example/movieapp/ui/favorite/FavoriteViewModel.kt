package com.example.movieapp.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor() : ViewModel() {

    private val _movieList = MutableLiveData<List<Movie>>().apply {
        value = emptyList()
    }

    val movieList: LiveData<List<Movie>> = _movieList
}