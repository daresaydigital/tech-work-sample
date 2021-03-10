package com.daresay.movies.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.daresay.movies.data.models.movie.Movie
import com.daresay.movies.databinding.FragmentFavoritesBinding
import com.daresay.movies.ui.activities.MovieDetailsActivity
import com.daresay.movies.ui.adapters.FavoriteMoviesAdapter
import com.daresay.movies.ui.callbacks.MovieOnClickListener
import com.daresay.movies.ui.viewmodels.MovieFavoritesViewModel
import com.daresay.movies.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment(), MovieOnClickListener {
    private val viewModel: MovieFavoritesViewModel by viewModels()
    private lateinit var binding: FragmentFavoritesBinding
    private val favoriteMoviesAdapter = FavoriteMoviesAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)

        setUpFavoriteMoviesList()
        setUpFavoriteMoviesObserver()

        return binding.root
    }

    private fun setUpFavoriteMoviesList() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.favoriteRecyclerView.layoutManager = layoutManager
        binding.favoriteRecyclerView.adapter = favoriteMoviesAdapter
    }

    private fun setUpFavoriteMoviesObserver() {
        viewModel.getAllFavorites().observe(viewLifecycleOwner, { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    resource.data?.let { movies ->
                        favoriteMoviesAdapter.setItems(movies.map { it.movieDetails })
                    }
                }

                Resource.Status.ERROR -> {

                }

                Resource.Status.LOADING -> {

                }
            }
        })
    }

    override fun onMovieClicked(movieId: Int) {
        val intent = Intent(requireContext(), MovieDetailsActivity::class.java)
        intent.putExtra("movie_id", movieId)
        startActivity(intent)
    }
}