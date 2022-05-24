package ir.sass.movie.ui.fragments.mainList

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ir.sass.base_data.model.toJsonString
import ir.sass.base_ui.*
import ir.sass.domain.model.ResultModel
import ir.sass.movie.ui.R
import ir.sass.movie.ui.databinding.FragmentMovieListBinding
import ir.sass.movie.ui.databinding.ItemMovieListBinding
import ir.sass.shared_domain.MovieListType

@AndroidEntryPoint
class MovieListFragment : MotherFragment<FragmentMovieListBinding>(
    MotherFragmentSetting(
        R.layout.fragment_movie_list,
        "Movies"
    )
) {
    private val viewModel: MovieListFragmentViewModel by viewModels()

    private val args by navArgs<MovieListFragmentArgs>()
    lateinit var type : MovieListType

    private val adapter = MotherAdapter<ItemMovieListBinding, ResultModel>(
        RecyclerItemWrapper(R.layout.item_movie_list,
            ended = {
                viewModel.getMovies(type)
            }
            ) { binding, item, pos ->
            binding.movie = item
            binding.txtTitle.isSelected = true
            binding.navigate = {
                findNavController().navigate(
                    MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(
                        toJsonString(item), type.ordinal
                    )
                )
            }
        }
    )

    override fun binding() {
        type = MovieListType.values()[args.type]
        connectViewModelForLoadingAndError(viewModel)

        coroutinesLauncher(Lifecycle.State.CREATED){
            viewModel.getMovies(type)
        }


        dataBinding.adapter = adapter
        dataBinding.lifecycleOwner = viewLifecycleOwner
        dataBinding.recyclerview.setHasFixedSize(true)
        dataBinding.recyclerview.layoutManager = GridLayoutManager(requireContext(),2)


        coroutinesLauncher(Lifecycle.State.STARTED) {
            viewModel.movies.collect {
                it?.let {
                    it.results?.let {
                        dataBinding.emptyState = it.isEmpty() && (adapter.itemCount == 0 || MovieListType.values()[args.type] == MovieListType.FAVORITE)
                        adapter.addToList(it)
                    }
                }
            }
        }
    }
}