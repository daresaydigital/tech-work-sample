package com.mousavi.hashem.mymoviesapp.presentaion.explore.details

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.helper.widget.Flow
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import coil.load
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.mousavi.hashem.mymoviesapp.R
import com.mousavi.hashem.mymoviesapp.domain.model.Movie
import com.mousavi.hashem.mymoviesapp.presentaion.BaseFragment
import com.mousavi.hashem.util.dateFormat
import com.mousavi.hashem.util.dp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class DetailsFragment : BaseFragment(R.layout.fragment_details) {

    companion object {
        const val ARG_MOVIE = "arg_movie"
    }

    private val viewModel: DetailsViewModel by viewModels()

    private lateinit var backDropImageView: ImageView
    private lateinit var posterImageView: ImageView
    private lateinit var rateTextView: TextView
    private lateinit var allVotesTextView: TextView
    private lateinit var titleTextView: TextView
    private lateinit var overviewTextView: TextView
    private lateinit var backButton: MaterialButton
    private lateinit var favoriteButton: MaterialButton
    private lateinit var flowLayout: ConstraintLayout
    private lateinit var flowHelper: Flow
    private lateinit var releaseDateTextView: TextView

    private lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movie = arguments?.getParcelable(ARG_MOVIE)
            ?: throw IllegalArgumentException("Movie must be passed")

        sharedElementEnterTransition =
            TransitionInflater.from(context ?: return).inflateTransition(android.R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.checkIsFavorite(movie)
        bindViews(view)
        listeners(view)
        observers()
        showData(movie)
    }

    private fun bindViews(view: View) {
        backDropImageView = view.findViewById(R.id.imageview_backdrop)
        posterImageView = view.findViewById(R.id.imageview_poster)
        rateTextView = view.findViewById(R.id.tv_rate)
        allVotesTextView = view.findViewById(R.id.tv_all_votes)
        titleTextView = view.findViewById(R.id.tv_title)
        overviewTextView = view.findViewById(R.id.tv_overview)
        backButton = view.findViewById(R.id.btn_back)
        favoriteButton = view.findViewById(R.id.btn_add_remove_favorite)
        flowLayout = view.findViewById(R.id.flow_layout)
        flowHelper = view.findViewById(R.id.flow)
        releaseDateTextView = view.findViewById(R.id.tv_release_date)
    }

    private fun listeners(view: View) {
        backButton.setOnClickListener { onBackPressed() }
        favoriteButton.setOnClickListener {
            if (!viewModel.ifFavorite.value) {
                viewModel.saveAsFavorite(movie)
                Snackbar.make(view,
                    getString(R.string.message_add_to_favorite),
                    Snackbar.LENGTH_SHORT).show()
            } else {
                viewModel.deleteFavorite(movie)
                Snackbar.make(view,
                    getString(R.string.message_removed_from_favorite),
                    Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun observers() {
        lifecycleScope.launchWhenStarted {
            viewModel.ifFavorite.collectLatest { isFavorite ->
                favoriteButton.setIconResource(if (isFavorite) R.drawable.ic_bookmark else R.drawable.ic_not_bookmark)
            }
        }
    }

    private fun showData(movie: Movie) {
        with(movie) {
            backDropImageView.load(backdropPath)
            posterImageView.load(posterPath)
            rateTextView.text = voteAverage.toString()
            allVotesTextView.text = context?.getString(R.string.votes_place_holder, voteCount)
            titleTextView.text = title
            overviewTextView.text = overview
            releaseDateTextView.text = dateFormat(releaseDate)

            val listOfGenreViews = createGenresVIew()

            flowHelper.referencedIds = IntArray(listOfGenreViews.size) {
                listOfGenreViews[it].id
            }

            listOfGenreViews.forEach {
                flowLayout.addView(it)
            }
        }
    }

    private fun Movie.createGenresVIew(): List<MaterialButton> {
        val listOfGenreViews = mutableListOf<MaterialButton>()
        this.genreNames.forEach { genre ->
            val materialButton = MaterialButton(context ?: return emptyList(),
                null,
                android.R.style.Widget_Material_Button_Small).apply {
                id = View.generateViewId()
                text = genre
                isAllCaps = false
                strokeWidth = 1.dp
                setStrokeColorResource(R.color.gray)
                cornerRadius = 4.dp
                insetBottom = 0
                insetTop = 0
                includeFontPadding = false
                minHeight = 0
                minWidth = 0
                setPadding(8.dp, 4.dp, 8.dp, 4.dp)
                setTextColor(ContextCompat.getColor(context ?: return emptyList(),
                    R.color.on_surface))
                backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(context ?: return emptyList(),
                        R.color.trasparent)
                )
            }
            listOfGenreViews.add(materialButton)
        }
        return listOfGenreViews
    }


    override fun onBackPressed() {
        findNavController().popBackStack()
    }

}