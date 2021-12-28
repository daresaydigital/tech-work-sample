package com.daresaydigital.presentation.feature.movie_details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.daresaydigital.core.utils.NetworkConstants
import com.daresaydigital.domain.features.movie_details.model.MovieDetails
import com.daresaydigital.domain.model.Movie
import com.daresaydigital.presentation.R
import com.daresaydigital.presentation.base.BaseActivity
import com.daresaydigital.presentation.util.ImageLoader
import com.daresaydigital.presentation.util.extensions.observeNullSafe
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_movie_details.*
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@AndroidEntryPoint
class MovieDetailsActivity : BaseActivity<MovieDetailsViewModel>(){

    companion object {

        const val SELECTED_MOVIE = "SELECTED_MOVIE"

        fun getLaunchIntent(context: Context, movie:Movie? = null): Intent {
            return Intent(context, MovieDetailsActivity::class.java).apply {
                movie?.let {
                    this.putExtra(SELECTED_MOVIE,it)
                }
            }
        }
    }

    private var selectedMovie: Movie? = null

    override val viewModel: MovieDetailsViewModel by viewModels()

    override fun layoutId(): Int = R.layout.activity_movie_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        selectedMovie = intent.getSerializableExtra(SELECTED_MOVIE) as? Movie

        setupViews()
        setupObservers()
        
        selectedMovie?.id?.let {
            viewModel.getMovieDetails(it)
        }
    }

    private fun setupViews(){
        ibBack.setOnClickListener {
            onBackPressed()
        }
        ibFav.setOnClickListener {
            selectedMovie?.let { it1 -> viewModel.changeFavouriteState(it1) }
        }
    }

    private fun setupObservers(){
        viewModel.progressVisibilityLiveData.observeNullSafe(this){
            if (it){
                frmLoading.visibility = View.VISIBLE
            }
            else {
                frmLoading.visibility = View.GONE
            }
        }

        viewModel.failureEventLiveData.observeNullSafe(this){ message ->
            viewFailureError(message ?: getString(R.string.something_went_wrong)) {
                selectedMovie?.let { it1 ->
                    viewModel.getMovieDetails(it1.id)
                }
            }
        }

        viewModel.movieListLiveData.observeNullSafe(this){ movieDetails ->
            updateViews(movieDetails)
        }

        viewModel.movieIsFavouriteLiveData.observeNullSafe(this){ it ->
            if (it){
                ibFav.setImageResource(R.drawable.ic_fav)
            } else {
                ibFav.setImageResource(R.drawable.ic_empty_fav)
            }
        }
    }

    private fun updateViews(movieDetails: MovieDetails) {
        with(movieDetails){
            tvTitle.text = title
            tvDescription.text = overview
            tvRate.text = "${getString(R.string.rate)} ${voteAverage}"
            tvReleaseDate.text = "${getString(R.string.release_date)} ${releaseDate}"

            ImageLoader.loadWithDefaultSize(ivPoster, NetworkConstants.BASE_URL_IMAGE_ORIGINAL + posterPath)
            ImageLoader.loadWithDefaultSize(ivBackgroundImage, NetworkConstants.BASE_URL_IMAGE_ORIGINAL + posterPath)
        }
    }

    private fun viewFailureError(message: String, listener: View.OnClickListener) {
        val snackBar = Snackbar.make(
            mainLayout,
            message, Snackbar.LENGTH_INDEFINITE
        )
        snackBar.setAction(R.string.retry, listener)
        snackBar.show()
    }
}