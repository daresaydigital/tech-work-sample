package ir.hamidbazargan.daresayassignment.presentation.movielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import ir.hamidbazargan.daresayassignment.databinding.ItemPopularMovieBinding
import ir.hamidbazargan.daresayassignment.domain.entity.MovieEntity

class MovieListAdapter(
    diffCallback: DiffUtil.ItemCallback<MovieEntity>,
    private val clickListener: ClickListener
) : PagingDataAdapter<MovieEntity, MovieViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            ItemPopularMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            clickListener
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { movieEntity ->
            holder.bind(movieEntity)
        }
    }
}