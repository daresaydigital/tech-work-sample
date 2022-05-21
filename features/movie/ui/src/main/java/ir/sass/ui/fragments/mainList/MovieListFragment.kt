package ir.sass.ui.fragments.mainList

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import dagger.hilt.android.AndroidEntryPoint
import ir.sass.base_ui.MotherFragment
import ir.sass.base_ui.MotherFragmentSetting
import ir.sass.ui.R
import ir.sass.ui.databinding.FragmentMovieListBinding
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MovieListFragment : MotherFragment<FragmentMovieListBinding>(
    MotherFragmentSetting(
        R.layout.fragment_movie_list,
        "Movies"
    )
) {

    private val viewModel : MovieListFragmentViewModel by viewModels()

    override fun binding() {
        connectViewModelForLoadingAndError(viewModel)

        viewModel.getMovies()

        coroutinesLauncher(Lifecycle.State.STARTED){
            viewModel.movies.collect{
                it?.let {

                }
            }
        }
    }
}