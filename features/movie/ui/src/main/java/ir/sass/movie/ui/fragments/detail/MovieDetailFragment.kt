package ir.sass.movie.ui.fragments.detail

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ir.sass.base_data.model.toReal
import ir.sass.base_ui.MotherFragment
import ir.sass.base_ui.MotherFragmentSetting
import ir.sass.base_ui.utils.toast
import ir.sass.domain.model.ResultModel
import ir.sass.movie.ui.R
import ir.sass.movie.ui.databinding.FragmentMovieDetailBinding
import ir.sass.shared_domain.MovieListType

@AndroidEntryPoint
class MovieDetailFragment : MotherFragment<FragmentMovieDetailBinding>(
    MotherFragmentSetting(
        R.layout.fragment_movie_detail,
        "Detail"
    )
) {

    private val args by navArgs<MovieDetailFragmentArgs>()

    private val viewModel: MovieDetailFragmentViewModel by viewModels()

    override fun binding() {
        toReal<ResultModel>(args.data)?.let {
            dataBinding.data = it
            viewModel.resultModel = it
            viewModel.type = MovieListType.values()[args.type]
        }

        dataBinding.viewModel = viewModel
        dataBinding.lifecycleOwner = viewLifecycleOwner

        viewModel.checkItemExists()


        coroutinesLauncher(Lifecycle.State.STARTED) {
            viewModel.message.collect {
                requireContext().toast(it)
            }
        }
    }
}