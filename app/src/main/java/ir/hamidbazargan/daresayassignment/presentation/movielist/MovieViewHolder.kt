package ir.hamidbazargan.daresayassignment.presentation.movielist

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ir.hamidbazargan.daresayassignment.R
import ir.hamidbazargan.daresayassignment.databinding.ItemPopularMovieBinding
import ir.hamidbazargan.daresayassignment.domain.entity.MovieEntity

class MovieViewHolder(
    private val itemPopularMovieBinding: ItemPopularMovieBinding,
    private val clickListener: ClickListener
) : RecyclerView.ViewHolder(itemPopularMovieBinding.root) {

    fun bind(movieEntity: MovieEntity) {
        itemPopularMovieBinding.root.setOnClickListener {
            clickListener.onMovieClick(movieEntity)
        }
        with(itemPopularMovieBinding) {
            title.text = movieEntity.title
            overview.text = movieEntity.overview
            rating.progress = movieEntity.voteAverage.toInt()
            Glide.with(root.context)
                .load("$BASE_URL${movieEntity.posterPath}")
                .centerCrop()
                .placeholder(R.drawable.ic_baseline_image_24)
                .into(image)
        }
    }

    private companion object {
        //TODO We have to use configuration to get base url and the available file size
        const val BASE_URL = "https://image.tmdb.org/t/p/w185"
    }
}