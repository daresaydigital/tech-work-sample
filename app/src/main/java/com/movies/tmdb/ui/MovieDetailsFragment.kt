package com.movies.tmdb.ui

import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.RequestManager
import com.movies.tmdb.R
import com.movies.tmdb.other.Extensions.base64ToBitmap
import com.movies.tmdb.other.Extensions.toBase64String
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie_details.*
import javax.inject.Inject


@AndroidEntryPoint
class MovieDetailsFragment @Inject constructor(
    val glide: RequestManager
) : ParentFragment(R.layout.fragment_movie_details) {

    val args: MovieDetailsFragmentArgs by navArgs()

    override fun startViewActions() {
         setViews()

    }


    private fun saveMovieOffline() {
        val selectedMovie = args.selectedMovie
        selectedMovie.imageBase64 = movieImage.drawable.toBitmap().toBase64String()
        viewModel.insertShoppingItemIntoDb(selectedMovie)
    }

    private fun setViews() {


        var selectedMovie = args.selectedMovie

        if (selectedMovie.imageBase64.isNullOrEmpty()) {
            glide.load(selectedMovie.getPosterFullImage())
                .into(movieImage)
        } else {
            movieImage.setImageBitmap(selectedMovie.imageBase64!!.base64ToBitmap())

        }

        showViewsButton.setOnClickListener {

            findNavController().navigate(

                MovieDetailsFragmentDirections.actionMovieDetailsFragmentToMovieReviewsFragment(args.selectedMovie.id)
            )
        }

        movieName.text = selectedMovie.originalTitle
        circle_progress_view.setProgressWithAnimation(selectedMovie.voteAverage * 10, 2000)
        overViewDetails.text = selectedMovie.overview

        viewModel.isMovieExistOffline(selectedMovie.id)
        viewModel.isMovieExist.observe(viewLifecycleOwner, Observer {
            saveOfflineButton.isChecked = it
        })


        saveOfflineButton.setOnCheckedChangeListener { compoundButton, b ->

            when (compoundButton.isChecked) {
                true -> saveMovieOffline()
                false ->  viewModel.deleteShoppingItem(selectedMovie.id)
            }
        }
    }
}




















