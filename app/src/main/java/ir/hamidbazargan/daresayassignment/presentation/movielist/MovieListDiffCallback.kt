package ir.hamidbazargan.daresayassignment.presentation.movielist

import androidx.recyclerview.widget.DiffUtil
import ir.hamidbazargan.daresayassignment.domain.entity.MovieEntity

class MovieListDiffCallback : DiffUtil.ItemCallback<MovieEntity>() {

    override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
        return (oldItem.voteCount == newItem.voteCount)
            .and(
                oldItem.voteAverage.equals(newItem.voteAverage)
                    .and(
                        oldItem.popularity.equals(newItem.popularity)
                    )
            )
    }

}