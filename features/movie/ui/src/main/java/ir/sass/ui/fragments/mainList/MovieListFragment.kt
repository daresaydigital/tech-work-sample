package ir.sass.ui.fragments.mainList

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import dagger.hilt.android.AndroidEntryPoint
import ir.sass.base_ui.MotherAdapter
import ir.sass.base_ui.MotherFragment
import ir.sass.base_ui.MotherFragmentSetting
import ir.sass.base_ui.RecyclerItemWrapper
import ir.sass.domain.model.ResultModel
import ir.sass.ui.R
import ir.sass.ui.databinding.FragmentMovieListBinding
import ir.sass.ui.databinding.ItemMovieListBinding

@AndroidEntryPoint
class MovieListFragment : MotherFragment<FragmentMovieListBinding>(
    MotherFragmentSetting(
        R.layout.fragment_movie_list,
        "Movies"
    )
) {

    private val viewModel : MovieListFragmentViewModel by viewModels()

    private val adapter = MotherAdapter<ItemMovieListBinding, ResultModel>(
        RecyclerItemWrapper(R.layout.item_movie_list){ binding, item, pos ->
            binding.movie = item
            binding.navigate = {

            }
        }
    )

    override fun binding() {
        connectViewModelForLoadingAndError(viewModel)

        viewModel.getMovies()
        dataBinding.adapter = adapter

        coroutinesLauncher(Lifecycle.State.STARTED){
            viewModel.movies.collect{
                it?.let {
                    it.results?.let{
                        adapter.changeList(it)
                    }
                }
            }
        }
    }
}