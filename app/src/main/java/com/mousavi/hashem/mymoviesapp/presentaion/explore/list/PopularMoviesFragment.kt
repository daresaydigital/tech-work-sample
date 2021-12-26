package com.mousavi.hashem.mymoviesapp.presentaion.explore.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mousavi.hashem.mymoviesapp.R
import com.mousavi.hashem.mymoviesapp.presentaion.BaseFragment
import com.mousavi.hashem.mymoviesapp.presentaion.explore.details.DetailsFragment
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
            view.transitionName =
                context?.getString(R.string.transition_name_poster) ?: return@PopularMoviesAdapter
            val extras =
                FragmentNavigatorExtras(
                    view to context!!.getString(R.string.transition_name_poster)
                )

            findNavController().navigate(
                resId = R.id.action_popularMoviesFragment_to_detailsFragment,
                args = Bundle().apply {
                    putParcelable(DetailsFragment.ARG_MOVIE, movie)
                },
                navOptions = null,
                navigatorExtras = extras
            )
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getPopularMovies()
    }

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
                adapter.appendData(pageData.movies, pageData.page, pageData.totalPages)
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