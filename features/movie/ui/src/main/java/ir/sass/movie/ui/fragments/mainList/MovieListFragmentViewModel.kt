package ir.sass.movie.ui.fragments.mainList

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.sass.base_ui.MotherViewModel
import ir.sass.domain.model.DiscoverMovieModel
import ir.sass.domain.usecase.DiscoverMovieUseCase
import ir.sass.domain.usecase.DiscoverMyFavoriteMoviesOfflineUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListFragmentViewModel @Inject constructor(
    private val discoverMovieUseCase : DiscoverMovieUseCase,
    private val discoverMyFavoriteMoviesOfflineUseCase : DiscoverMyFavoriteMoviesOfflineUseCase
): MotherViewModel() {

    private val _movies : MutableStateFlow<DiscoverMovieModel?> = MutableStateFlow(null)
    val movies : StateFlow<DiscoverMovieModel?> = _movies

    fun getMovies(isFavorite : Boolean){
        if(isFavorite){
            action(discoverMyFavoriteMoviesOfflineUseCase(),true){
                viewModelScope.launch {
                    _movies.emit(it)
                }
            }
        }else{
            action(discoverMovieUseCase(),true){
                viewModelScope.launch {
                    _movies.emit(it)
                }
            }
        }

    }

}