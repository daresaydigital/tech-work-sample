package com.mousavi.hashem.mymoviesapp.presentaion.reviews

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mousavi.hashem.mymoviesapp.R
import com.mousavi.hashem.mymoviesapp.presentaion.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ReviewsFragment : BaseFragment(R.layout.fragment_reviews) {

    companion object {
        const val ARG_MOVIE_ID = "arg_movie_id"
    }

    private val viewModel: ReviewsViewModel by viewModels()

    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView

    private val adapter = ReviewsAdapter(
        onLoadMoreListener = { newPage ->

        }
    )

    private var movieId: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movieId = arguments?.getInt(ARG_MOVIE_ID)
            ?: throw IllegalArgumentException("Movie Id must be passed")

        viewModel.getReviews(movieId)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        listeners()
        observers()
        recycler()
    }

    private fun observers() {
        lifecycleScope.launchWhenStarted {
            viewModel.reviews.collectLatest {
                adapter.appendData(it.reviews, it.page)
            }
        }


        lifecycleScope.launchWhenStarted {
            viewModel.loading.collectLatest {
                adapter.isLoading = it
            }
        }
    }

    private fun bindViews(view: View) {
        toolbar = view.findViewById(R.id.toolbar)
        recyclerView = view.findViewById(R.id.recycler_view)
    }

    private fun listeners() {
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun recycler() {
        recyclerView.adapter = adapter
    }

    override fun onBackPressed() {
        findNavController().popBackStack()
    }
}