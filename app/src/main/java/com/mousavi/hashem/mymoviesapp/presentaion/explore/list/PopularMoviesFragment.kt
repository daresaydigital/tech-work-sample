package com.mousavi.hashem.mymoviesapp.presentaion.explore.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.mousavi.hashem.mymoviesapp.R
import com.mousavi.hashem.mymoviesapp.presentaion.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class PopularMoviesFragment : BaseFragment(R.layout.fragment_popular_movies) {

    private val viewModel: PopularMoviesViewModel by viewModels()

    private lateinit var recyclerView: RecyclerView

    private val adapter = PopularMoviesAdapter(
        onLoadMoreListener = { newPage ->
            viewModel.getPopularMovies(
                page = newPage
            )
        },
        onItemClicked = { movie, view ->
            //TODO()
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView(view)
        observers()
        recycler()
    }

    private fun bindView(view: View) {
        recyclerView = view.findViewById(R.id.recycler_view)
    }

    private fun recycler() {
        recyclerView.adapter = adapter
    }

    private fun observers() {
        lifecycleScope.launchWhenStarted {
            viewModel.popularMovies.collectLatest { pageData ->
                adapter.appendData(pageData.movies, pageData.page)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.popularMoviesLoading.collectLatest {
                adapter.isLoading = it
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        activity?.finish()
    }

}