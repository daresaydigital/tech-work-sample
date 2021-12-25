package com.mousavi.hashem.mymoviesapp.presentaion.favorite

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mousavi.hashem.mymoviesapp.R
import com.mousavi.hashem.mymoviesapp.presentaion.BaseFragment
import com.mousavi.hashem.mymoviesapp.presentaion.MainActivity
import com.mousavi.hashem.mymoviesapp.presentaion.explore.details.DetailsFragment
import com.mousavi.hashem.util.showGone
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class FavoritesFragment : BaseFragment(R.layout.fragment_favorites) {

    private val viewModel: FavoritesViewModel by viewModels()

    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyStateTextView: TextView

    private val adapter = FavoriteMoviesAdapter(
        onItemClicked = { movie, view ->
            view.transitionName =
                context?.getString(R.string.transition_name_poster) ?: return@FavoriteMoviesAdapter
            val extras =
                FragmentNavigatorExtras(
                    view to context!!.getString(R.string.transition_name_poster)
                )

            findNavController().navigate(
                resId = R.id.action_favoritesFragment_to_detailsFragment,
                args = Bundle().apply {
                    putParcelable(DetailsFragment.ARG_MOVIE, movie)
                },
                navOptions = null,
                navigatorExtras = extras
            )
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        recycler()
        observers()
    }

    private fun observers() {
        lifecycleScope.launchWhenStarted {
            viewModel.favorites.collectLatest {
                adapter.items = it
                emptyStateTextView.showGone(it.isNullOrEmpty())
            }
        }
    }

    private fun bindViews(view: View) {
        recyclerView = view.findViewById(R.id.recycler_view)
        emptyStateTextView = view.findViewById(R.id.tv_empty_state)
    }

    private fun recycler() {
        recyclerView.adapter = adapter
    }

    override fun onBackPressed() {
        (activity as? MainActivity)?.selectExplorePage()
    }

}