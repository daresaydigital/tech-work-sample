package com.example.movieapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.FragmentHomeBinding
import com.example.movieapp.ui.MovieRecyclerViewAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var homeMovieAdapter: MovieRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        binding.homeRecyclerView.layoutManager = layoutManager

        homeViewModel.movieList.value?.let {
            if (it.isNotEmpty()) {
                binding.homeEmptyListTextView.visibility = View.GONE
                binding.homeRecyclerView.visibility = View.VISIBLE
                homeMovieAdapter = MovieRecyclerViewAdapter(it)
                binding.homeRecyclerView.adapter = homeMovieAdapter
            } else {
                binding.homeEmptyListTextView.visibility = View.VISIBLE
                binding.homeRecyclerView.visibility = View.GONE
            }
        } ?: run {
            binding.homeRecyclerView.visibility = View.GONE
            binding.homeEmptyListTextView.visibility = View.VISIBLE
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}