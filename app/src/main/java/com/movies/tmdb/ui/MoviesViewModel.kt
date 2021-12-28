package com.movies.tmdb.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movies.tmdb.data.remote.responses.MovieObject
import com.movies.tmdb.data.remote.responses.MoviesResponse
import com.movies.tmdb.data.remote.responses.ReviewResponse
import com.movies.tmdb.other.Event
import com.movies.tmdb.other.Resource
import com.movies.tmdb.repositories.MoviesRepository
import kotlinx.coroutines.launch

class MoviesViewModel @ViewModelInject constructor(
    private val repository: MoviesRepository
) : ViewModel() {

    val shoppingItems = repository.observeAllShoppingItems()
    val isConnected = MutableLiveData<Boolean>()


    private val _moviesList = MutableLiveData<Event<Resource<MoviesResponse>>>()
    val moviesList: MutableLiveData<Event<Resource<MoviesResponse>>> = _moviesList


    private val _reviewsList = MutableLiveData<Event<Resource<ReviewResponse>>>()
    val reviewsList: LiveData<Event<Resource<ReviewResponse>>> = _reviewsList

    private val _isMovieExist = MutableLiveData<Boolean>()
    val isMovieExist: MutableLiveData<Boolean> = _isMovieExist


    fun insertShoppingItemIntoDb(shoppingItem: MovieObject) = viewModelScope.launch {
        repository.insertShoppingItem(shoppingItem)
    }

    fun deleteShoppingItem(id: Int) = viewModelScope.launch {
        repository.deleteShoppingItem(id)
    }


    fun isMovieExistOffline(id: Int) = viewModelScope.launch {
        _isMovieExist.value = repository.isMovieExistsOffline(id)
    }


    fun getMoviesListFromWeb(filterId: Int, page: Int) {

        _moviesList.value = Event(Resource.loading(null))
        viewModelScope.launch {
            val response = repository.getListOfMovies(filterId, page)
            _moviesList.value = Event(response)
        }
    }

    fun getMovieReviewsFromWeb(movieId: Int, page: Int) {
        _reviewsList.value = Event(Resource.loading(null))
        viewModelScope.launch {
            val response = repository.getMovieReviews(movieId, page)
            _reviewsList.value = Event(response)
        }
    }
}













