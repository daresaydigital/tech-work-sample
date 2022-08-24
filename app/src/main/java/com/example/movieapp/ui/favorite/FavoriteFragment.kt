package com.example.movieapp.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.FragmentFavoriteBinding
import com.example.movieapp.ui.MovieRecyclerViewAdapter

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var favoriteMovieAdapter: MovieRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val favoriteViewModel =
            ViewModelProvider(this).get(FavoriteViewModel::class.java)

        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        binding.favoriteRecyclerView.layoutManager = layoutManager

        favoriteViewModel.movieList.value?.let {
            if (it.isNotEmpty()) {
                binding.favoriteEmptyListTextView.visibility = View.GONE
                binding.favoriteRecyclerView.visibility = View.VISIBLE
                favoriteMovieAdapter = MovieRecyclerViewAdapter(it)
                binding.favoriteRecyclerView.adapter = favoriteMovieAdapter
            } else {
                binding.favoriteEmptyListTextView.visibility = View.VISIBLE
                binding.favoriteRecyclerView.visibility = View.GONE
            }
        } ?: run {
            binding.favoriteRecyclerView.visibility = View.GONE
            binding.favoriteEmptyListTextView.visibility = View.VISIBLE
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}