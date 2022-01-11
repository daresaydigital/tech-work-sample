package ir.hamidbazargan.daresayassignment.presentation.movielist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ir.hamidbazargan.daresayassignment.R
import ir.hamidbazargan.daresayassignment.databinding.FragmentMovieListBinding
import ir.hamidbazargan.daresayassignment.domain.entity.MovieEntity
import ir.hamidbazargan.daresayassignment.presentation.main.MainFragmentDirections
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class MovieListFragment : Fragment(), ClickListener {

    abstract val movieListViewModel: MovieListViewModel

    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initRecycler()
        observeViewModel()
    }

    private fun initAdapter() {
        adapter = MovieListAdapter(MovieListDiffCallback(), this)
        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { state ->
                binding.loading.isVisible = state.refresh is LoadState.Loading
                when {
                    state.refresh is LoadState.Error -> state.refresh as LoadState.Error
                    state.append is LoadState.Error -> state.append as LoadState.Error
                    else -> null
                }?.let {
                    Snackbar.make(
                        binding.root,
                        it.error.message.toString(),
                        Snackbar.LENGTH_INDEFINITE
                    )
                        .setAction(R.string.retry) {
                            adapter.retry()
                        }
                        .setActionTextColor(
                            resources.getColor(android.R.color.holo_red_light))
                        .show()
                }
            }
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            movieListViewModel.pagerFlow.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun initRecycler() {
        LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false).also {
            binding.recycler.layoutManager = it
            binding.recycler.adapter = adapter
            binding.recycler.addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            binding.recycler.setHasFixedSize(true)
        }
    }

    override fun onMovieClick(movie: MovieEntity) {
        MainFragmentDirections.actionMainFragmentToDetailFragment(
            movie.id
        ).also { navDirections ->
            findNavController().navigate(navDirections)
        }
    }
}