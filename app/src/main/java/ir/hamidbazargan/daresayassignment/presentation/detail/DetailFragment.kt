package ir.hamidbazargan.daresayassignment.presentation.detail

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import ir.hamidbazargan.daresayassignment.R
import ir.hamidbazargan.daresayassignment.databinding.FragmentDetailBinding
import ir.hamidbazargan.daresayassignment.domain.entity.MovieEntity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class DetailFragment : Fragment() {

    private val detailViewModel by viewModel<DetailViewmodel>(named("detail"))

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        observeViewModel()
        detailViewModel.loadMovie(args.id)
    }

    private fun initToolbar() {
        findNavController().let { navController ->
            AppBarConfiguration(navController.graph).let { appBarConfiguration ->
                with(binding.toolbar) {
                    setupWithNavController(navController, appBarConfiguration)
                    initMenu()
                }
            }
        }
    }

    private fun Toolbar.initMenu() {
        inflateMenu(R.menu.menu_detail)
        setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_save -> {
                    detailViewModel.saveMovie()
                    true
                }
                else -> false
            }
        }
    }

    private fun observeViewModel() {
        detailViewModel.uiModel.observe(viewLifecycleOwner) { uiModel ->
            renderUiModel(uiModel)
        }
    }

    private fun renderUiModel(uiModel: UiModel) {
        with(binding) {
            loadingSection.isVisible = uiModel is UiModel.Loading
            movieSection.isVisible = (uiModel is UiModel.Success).or(uiModel is UiModel.Bookmark)
            messageSection.isVisible = uiModel is UiModel.Error
            when (uiModel) {
                is UiModel.Success -> {
                    loadMovieData(uiModel.movie)
                }
                is UiModel.Error -> {
                    showError(uiModel.errorMessage)
                }
                is UiModel.Bookmark -> {
                    showBookmar()
                }
            }
        }
    }

    private fun showBookmar() {
        Toast.makeText(context, getString(R.string.bookmard), Toast.LENGTH_SHORT).show()
    }

    private fun showError(errorMessage: String) {
        binding.messageSection.text = errorMessage
    }

    private fun loadMovieData(
        movie: MovieEntity
    ) {
        with(binding) {
            toolbar.title = movie.title
            movie.let { movie ->
                title.text = movie.title
                overview.text = movie.overview
                rating.progress = movie.voteAverage.toInt()
                release.text = movie.releaseDate
                Glide.with(root.context)
                    .load("$BASE_URL${movie.backdropPath}")
                    .centerCrop()
                    .placeholder(R.drawable.ic_baseline_image_24)
                    .into(poster)
            }
        }
    }

    private companion object {
        //TODO We have to use configuration to get base url and the available file size
        const val BASE_URL = "https://image.tmdb.org/t/p/w780/"
    }
}