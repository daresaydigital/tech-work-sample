package ir.sass.movie.ui.fragments.detail

import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ir.sass.base_data.model.toReal
import ir.sass.base_ui.MotherFragment
import ir.sass.base_ui.MotherFragmentSetting
import ir.sass.domain.model.ResultModel
import ir.sass.movie.ui.R
import ir.sass.movie.ui.databinding.FragmentMovieDetailBinding

@AndroidEntryPoint
class MovieDetailFragment : MotherFragment<FragmentMovieDetailBinding>(
    MotherFragmentSetting(
        R.layout.fragment_movie_detail,
        "Detail"
    )
) {

    private val args by navArgs<MovieDetailFragmentArgs>()

    override fun binding() {
        toReal<ResultModel>(args.data)?.let {
            dataBinding.data = it
        }
    }
}