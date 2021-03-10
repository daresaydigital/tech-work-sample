package com.daresay.movies.ui.activities

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.daresay.movies.R
import com.daresay.movies.databinding.ActivityMovieDetailsBinding
import com.daresay.movies.extensions.snack
import com.daresay.movies.ui.adapters.GenreItemAdapter
import com.daresay.movies.ui.adapters.ReviewAdapter
import com.daresay.movies.ui.viewmodels.MovieDetailsViewModel
import com.daresay.movies.utils.Resource
import com.daresay.movies.utils.getMoviePosterBigUrl
import com.google.android.flexbox.FlexboxLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlin.collections.ArrayList

@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity() {
    private val viewModel: MovieDetailsViewModel by viewModels()
    private lateinit var binding: ActivityMovieDetailsBinding
    private val genreItemAdapter = GenreItemAdapter()
    private val reviewItemAdapter = ReviewAdapter()

    private var reviewPage = 1
    private var reviewsMaxPage = 1
    private var reviewPageLoading = false

    private var movieId: Int = -1
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        movieId = intent.getIntExtra("movie_id", -1)
        if (movieId == -1) {
            // TODO: Throw error
        }

        setUpGenreRecyclerView()
        setUpReviewRecyclerView()
        setUpMovieDetailsObserver(movieId = movieId)
        setUpFavoriteObserver(movieId)

        binding.loading = true
    }

    private fun setUpGenreRecyclerView() {
        val layoutManager = FlexboxLayoutManager(this)
        binding.genreRecyclerView.layoutManager = layoutManager
        binding.genreRecyclerView.adapter = genreItemAdapter
    }

    private fun setUpReviewRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        binding.reviewsRecyclerView.layoutManager = layoutManager
        binding.reviewsRecyclerView.adapter = reviewItemAdapter
        binding.reviewsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (reviewPage == reviewsMaxPage)
                    return

                if (dx > 0) {
                    val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                    if (!reviewPageLoading && lastVisibleItem > reviewItemAdapter.itemCount - 5) {
                        reviewPageLoading = true
                        loadReviews(++reviewPage)
                    }
                }
            }
        })
    }

    private fun setUpMovieDetailsObserver(movieId: Int) {
        viewModel.getMovieDetails(movieId).observe(this, { it ->
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    it.data?.let { movieDetails ->
                        binding.movieDetails = movieDetails

                        reviewsMaxPage = movieDetails.reviews.total_pages
                        reviewItemAdapter.setItems(ArrayList(movieDetails.reviews.results))

                        genreItemAdapter.setItems(movieDetails.genres.map { genre ->
                            genre.name
                        })

                        Glide.with(binding.root)
                                .load(getMoviePosterBigUrl(movieDetails.poster_path))
                                .transform(CenterCrop())
                                .into(binding.movieImage)

                        binding.loading = false
                    }
                }

                Resource.Status.ERROR -> {
                    binding.root.snack(R.string.activity_movie_details_error_failed_to_load_movie)
                }

                Resource.Status.LOADING -> {
                    binding.loading = true
                }
            }
        })
    }

    private fun loadReviews(page: Int) {
        viewModel.getMovieReviews(movieId, page).observe(this, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    it.data?.let { reviews ->
                        reviewItemAdapter.addItems(ArrayList(reviews.results))
                        reviewPageLoading = false
                    }
                }

                Resource.Status.ERROR -> {

                }

                Resource.Status.LOADING -> {

                }
            }
        })
    }

    private fun setUpFavoriteObserver(movieId: Int) {
        viewModel.getFavorite(movieId).observe(this, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    it.data?.let { obj ->
                        setFavoriteButtonTint(obj.favorite)
                        isFavorite = obj.favorite
                    }
                }

                Resource.Status.ERROR -> {

                }

                Resource.Status.LOADING -> {

                }
            }
        })
    }

    fun onFavoriteClicked(view: View) {
        isFavorite = !isFavorite
        viewModel.setMovieFavorite(movieId, isFavorite)
    }

    private fun setFavoriteButtonTint(isFavorite: Boolean) {
        val colorRes = if (isFavorite) R.color.red else R.color.white
        binding.favorite.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, colorRes))
    }
}