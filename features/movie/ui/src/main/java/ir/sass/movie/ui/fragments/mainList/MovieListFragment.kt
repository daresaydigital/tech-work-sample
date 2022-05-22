package ir.sass.movie.ui.fragments.mainList

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ir.sass.base_data.model.toJsonString
import ir.sass.base_ui.MotherAdapter
import ir.sass.base_ui.MotherFragment
import ir.sass.base_ui.MotherFragmentSetting
import ir.sass.base_ui.RecyclerItemWrapper
import ir.sass.domain.model.ResultModel
import ir.sass.movie.ui.R
import ir.sass.movie.ui.databinding.FragmentMovieListBinding
import ir.sass.movie.ui.databinding.ItemMovieListBinding

@AndroidEntryPoint
class MovieListFragment : MotherFragment<FragmentMovieListBinding>(
    MotherFragmentSetting(
        R.layout.fragment_movie_list,
        "Movies"
    )
) {
    private val viewModel : MovieListFragmentViewModel by viewModels()

    private val args by navArgs<MovieListFragmentArgs>()

    private val adapter = MotherAdapter<ItemMovieListBinding, ResultModel>(
        RecyclerItemWrapper(R.layout.item_movie_list){ binding, item, pos ->
            binding.movie = item
            binding.navigate = {
                findNavController().navigate(MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(
                    toJsonString(item),args.isFavorite
                ))
            }
        }
    )

    override fun binding() {
        connectViewModelForLoadingAndError(viewModel)

        viewModel.getMovies(args.isFavorite)

        dataBinding.adapter = adapter

        coroutinesLauncher(Lifecycle.State.STARTED){
            viewModel.movies.collect{
                it?.let {
                    it.results?.let{
                        dataBinding.emptyState = it.isEmpty()
                        adapter.changeList(it)
                    }
                }
            }
        }
    }
}