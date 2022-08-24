package com.example.movieapp.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FavoriteViewModel : ViewModel() {

    private val _movieList = MutableLiveData<List<String>>().apply {
        value = emptyList()
    }

    val movieList: LiveData<List<String>> = _movieList
}