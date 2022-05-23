package ir.sass.movie.ui.fragments.mainList

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.sass.base_ui.MotherViewModel
import ir.sass.domain.model.DiscoverMovieModel
import ir.sass.domain.model.ResultModel
import ir.sass.domain.usecase.DiscoverPopularMovieUseCase
import ir.sass.domain.usecase.DiscoverMyFavoriteMoviesOfflineUseCase
import ir.sass.domain.usecase.DiscoverTopMovieUseCase
import ir.sass.shared_domain.MovieListType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListFragmentViewModel @Inject constructor(
    private val discoverPopularMovieUseCase: DiscoverPopularMovieUseCase,
    private val discoverTopMovieUseCase : DiscoverTopMovieUseCase,
    private val discoverMyFavoriteMoviesOfflineUseCase: DiscoverMyFavoriteMoviesOfflineUseCase
) : MotherViewModel() {

    private val _movies: MutableStateFlow<DiscoverMovieModel?> = MutableStateFlow(null)
    val movies: StateFlow<DiscoverMovieModel?> = _movies

    private var page = 0

    fun getMovies(type: MovieListType) {
        when(type){
            MovieListType.FAVORITE->{
                action(discoverMyFavoriteMoviesOfflineUseCase(), true) {
                    viewModelScope.launch {
                        _movies.emit(it)
                    }
                }
            }
            MovieListType.TOP_RATED->{
                action(discoverTopMovieUseCase(++page), true) {
                    viewModelScope.launch {
                        val newList = mutableListOf<ResultModel>()
                        _movies.value?.results?.let {
                            newList.addAll(it)
                        }
                        it.results?.let {
                            newList.addAll(it)
                        }
                        val copy = it.copy(
                            results = newList
                        )
                        _movies.emit(copy)
                    }
                }
            }
            MovieListType.POPULAR->{
                action(discoverPopularMovieUseCase(++page), true) {
                    viewModelScope.launch {
                        val newList = mutableListOf<ResultModel>()
                        _movies.value?.results?.let {
                            newList.addAll(it)
                        }
                        it.results?.let {
                            newList.addAll(it)
                        }
                        val copy = it.copy(
                            results = newList
                        )
                        _movies.emit(copy)
                    }
                }
            }
        }
    }

}