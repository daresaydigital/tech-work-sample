package com.example.movieapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.model.Movie
import com.example.movieapp.model.responses.MoviesResponse
import com.example.movieapp.repositories.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {

    private val _movieList = MutableLiveData<List<Movie>>().apply {
        value = listOf()
    }

    private val _message = MutableLiveData<String>().apply {
        value = "Loading..."
    }

    val movieList: LiveData<List<Movie>> = _movieList
    val message: LiveData<String> = _message

    init {
        movieRepository.loadPopularMovies().enqueue(object : retrofit2.Callback<MoviesResponse> {
            override fun onFailure(call: retrofit2.Call<MoviesResponse>, t: Throwable) {
                _message.value = t.message
            }

            override fun onResponse(
                call: retrofit2.Call<MoviesResponse>,
                response: retrofit2.Response<MoviesResponse>
            ) {
                if (response.isSuccessful) {
                    _movieList.value = response.body()?.movies
                } else {
                    _movieList.value = listOf()
                    _message.value = response.errorBody().toString()
                }
            }
        })
    }
}