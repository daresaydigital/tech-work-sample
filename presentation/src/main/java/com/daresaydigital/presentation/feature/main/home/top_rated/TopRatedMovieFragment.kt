package com.daresaydigital.presentation.feature.main.home.top_rated

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daresaydigital.domain.model.Movie
import com.daresaydigital.presentation.R
import com.daresaydigital.presentation.base.BaseFragment
import com.daresaydigital.presentation.feature.main.home.MovieAdapter
import com.daresaydigital.presentation.feature.movie_details.MovieDetailsActivity
import com.daresaydigital.presentation.util.extensions.observeNullSafe
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_top_rated.*
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@AndroidEntryPoint
class TopRatedMovieFragment : BaseFragment<TopRatedMovieViewModel>(){

    override val viewModel: TopRatedMovieViewModel by viewModels()

    override fun layoutId(): Int = R.layout.fragment_top_rated

    private var movieAdapter : MovieAdapter? = null
    private var layoutManager : GridLayoutManager? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupObservers()

        viewModel.getTopRatedPage()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        movieAdapter = null
    }

    private fun setupViews(){
        movieAdapter = MovieAdapter(mutableListOf()) { movie -> adapterOnClick(movie) }
        layoutManager = GridLayoutManager(requireContext(),2)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = movieAdapter
        recyclerView.addOnScrollListener(recyclerViewOnScrollListener)
    }

    private fun setupObservers(){
        viewModel.progressVisibilityLiveData.observeNullSafe(viewLifecycleOwner){
            if (it.first){
                if (it.second == 1) {
                    frmLoading.visibility = View.VISIBLE
                    bottomProgressbar.visibility = View.GONE
                } else {
                    bottomProgressbar.visibility = View.VISIBLE
                    frmLoading.visibility = View.GONE
                }
            }
            else {
                frmLoading.visibility = View.GONE
                bottomProgressbar.visibility = View.GONE
            }
        }

        viewModel.failureEventLiveData.observeNullSafe(viewLifecycleOwner){ pair ->
            viewFailureError(pair.first ?: getString(R.string.something_went_wrong)) {
                viewModel.getTopRatedPage(pair.second == 1)
            }
        }

        viewModel.movieListLiveData.observeNullSafe(viewLifecycleOwner){ movies ->
            movieAdapter?.setData(movies)
        }
    }

    private fun viewFailureError(message: String, listener: View.OnClickListener) {
        val snackBar = Snackbar.make(
            root,
            message, Snackbar.LENGTH_INDEFINITE
        )
        snackBar.setAction(R.string.retry, listener)
        snackBar.show()
    }

    private fun adapterOnClick(movie: Movie) {
        startActivity(
            MovieDetailsActivity.getLaunchIntent(requireContext(), movie)
        )
    }

    private val PAGE_SIZE = 20
    private val recyclerViewOnScrollListener: RecyclerView.OnScrollListener =
        object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount: Int = layoutManager?.childCount ?: 0
                val totalItemCount: Int = layoutManager?.itemCount ?: 0
                val firstVisibleItemPosition: Int = layoutManager?.findFirstVisibleItemPosition() ?: 0
                if (viewModel.progressVisibilityLiveData.value?.first == false && !viewModel.checkIsLastPage()) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= PAGE_SIZE) {
                        viewModel.getTopRatedPage(false)
                    }
                }
            }
        }
}