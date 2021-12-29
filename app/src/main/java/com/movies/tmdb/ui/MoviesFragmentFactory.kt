package com.movies.tmdb.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.movies.tmdb.adapters.MoviesAdapter
import com.movies.tmdb.adapters.ReviewsAdapter
import javax.inject.Inject

class MoviesFragmentFactory @Inject constructor(
    private val imageAdapter: MoviesAdapter,
    private val glide: RequestManager,
    private val reviewsAdapter: ReviewsAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            MoviesListingFragment::class.java.name -> MoviesListingFragment(imageAdapter)
            MovieDetailsFragment::class.java.name -> MovieDetailsFragment(glide)
            MovieReviewsFragment::class.java.name -> MovieReviewsFragment(reviewsAdapter)
            MovieReviewDetailsFragment::class.java.name -> MovieReviewDetailsFragment()
            OfflineMoviesListingFragment::class.java.name -> OfflineMoviesListingFragment(imageAdapter)
            else -> super.instantiate(classLoader, className)
        }
    }
}