package com.movies.tmdb.ui

import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.movies.tmdb.R
import com.movies.tmdb.adapters.ReviewsAdapter
import com.movies.tmdb.data.remote.responses.ReviewObject
import com.movies.tmdb.other.Extensions.toast
import com.movies.tmdb.other.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_reviews_listing.*
import javax.inject.Inject

@AndroidEntryPoint
class MovieReviewsFragment @Inject constructor(
    val imageAdapter: ReviewsAdapter
) : ParentFragment(R.layout.fragment_reviews_listing) {


    private var allReviews = mutableListOf<ReviewObject>()

    private var pageNum = 1
    var isLoading = false


    val args: MovieReviewsFragmentArgs by navArgs()

    override fun startViewActions() {

        setupRecyclerView()
        subscribeToObservers()


        getMovieReviews()


        imageAdapter.setOnItemClickListener {
            findNavController().navigate(
                MovieReviewsFragmentDirections.actionMovieReviewsFragmentToMovieReviewDetailsFragment(
                    it
                )

            )
        }
    }


    private fun getMovieReviews() {
        viewModel.getMovieReviewsFromWeb(args.selectedMovieId, pageNum)
        isLoading = true
    }


    private fun subscribeToObservers() {
        viewModel.reviewsList.observe(viewLifecycleOwner, Observer {
            it?.getContentIfNotHandled()?.let { result ->
                isLoading = false
                when (result.status) {
                    Status.SUCCESS -> {
                        progressBar.visibility = View.GONE
                        allReviews.addAll(result.data?.results!!)
                        if (allReviews.isEmpty()) {
                            requireContext().toast(getString(R.string.no_reviews))
                            findNavController().popBackStack()
                        } else {
                            imageAdapter.reviews = allReviews
                        }


                    }
                    Status.ERROR -> {
                        Snackbar.make(
                            requireActivity().rootLayout,
                            result.message ?: "An unknown error occured.",
                            Snackbar.LENGTH_LONG
                        ).show()
                        progressBar.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun setupRecyclerView() {
        val linearLayoutManger = LinearLayoutManager(requireContext())

        rvData.apply {
            adapter = imageAdapter
            layoutManager = linearLayoutManger
        }


        rvData.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = linearLayoutManger.childCount
                val pastVisibleItem = linearLayoutManger.findFirstCompletelyVisibleItemPosition()
                val total = imageAdapter.itemCount

                if (!isLoading) {
                    if ((visibleItemCount + pastVisibleItem) >= total) {
                        pageNum++
                        getMovieReviews()
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }


}
