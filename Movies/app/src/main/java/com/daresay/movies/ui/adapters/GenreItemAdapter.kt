package com.daresay.movies.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daresay.movies.databinding.AdapterGenreItemBinding

class GenreItemAdapter : RecyclerView.Adapter<GenreItemAdapter.GenreHolder>() {
    private var items: List<String> = emptyList()

    fun setItems(items: List<String>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreItemAdapter.GenreHolder {
        val binding = AdapterGenreItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenreHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: GenreHolder, position: Int) = holder.bind(items[position])

    inner class GenreHolder(private val binding: AdapterGenreItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var text: String

        fun bind(text: String) {
            this.text = text
            binding.name = text
        }
    }
}