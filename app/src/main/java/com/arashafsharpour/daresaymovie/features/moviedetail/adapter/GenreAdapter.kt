package com.arashafsharpour.daresaymovie.features.moviedetail.adapter

import androidx.databinding.ViewDataBinding
import com.arashafsharpour.daresaymovie.R
import com.arashafsharpour.daresaymovie.infrastructure.models.contracts.Genres
import com.arashafsharpour.daresaymovie.infrastructure.platform.BaseSimpleListAdapter
import com.skydoves.bindables.BR
import javax.inject.Inject

class GenreAdapter @Inject() constructor() :
    BaseSimpleListAdapter<Genres, ViewDataBinding>(Genres.DIFF_CALLBACK) {

    override fun getItemViewType(position: Int): Int = R.layout.item_genre

    override fun getItemVariableId(): Int = BR.genre

    override fun onBindViewHolder(holder: DataBindingViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}