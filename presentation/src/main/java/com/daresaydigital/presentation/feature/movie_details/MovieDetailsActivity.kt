package com.daresaydigital.presentation.feature.movie_details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.daresaydigital.domain.model.Movie
import com.daresaydigital.presentation.R
import com.daresaydigital.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_movie_details.*

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
    }

    private fun setupViews(){
        ibBack.setOnClickListener {
            onBackPressed()
        }
        ibFav.setOnClickListener {
            //todo on fav clicked
        }
    }
}