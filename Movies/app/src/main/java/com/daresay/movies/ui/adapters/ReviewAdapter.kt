package com.daresay.movies.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.daresay.movies.R
import com.daresay.movies.data.models.movie.Movie
import com.daresay.movies.data.models.moviedetails.Result
import com.daresay.movies.databinding.AdapterReviewItemBinding

class ReviewAdapter : RecyclerView.Adapter<ReviewAdapter.ReviewHolder>() {
    private var items: ArrayList<Result> = arrayListOf()

    fun setItems(items: ArrayList<Result>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun addItems(reviews: ArrayList<Result>) {
        val insertPosition = items.size
        this.items.addAll(reviews)
        notifyItemRangeInserted(insertPosition, reviews.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewAdapter.ReviewHolder {
        val binding = AdapterReviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ReviewHolder, position: Int) = holder.bind(items[position])

    inner class ReviewHolder(private val binding: AdapterReviewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var review: Result

        fun bind(review: Result) {
            this.review = review
            binding.result = review

            Glide.with(binding.root)
                    .load(review.author_details.avatar_path?.removePrefix("/"))
                    .transform(CenterCrop())
                    .error(R.drawable.avatar_placeholder)
                    .into(binding.avatar)
        }
    }
}