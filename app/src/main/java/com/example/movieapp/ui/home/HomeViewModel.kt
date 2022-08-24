package com.example.movieapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _movieList = MutableLiveData<List<String>>().apply {
        value = listOf("Movie 1", "Movie 2", "Movie 3")
    }

    val movieList: LiveData<List<String>> = _movieList
}