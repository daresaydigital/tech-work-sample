package com.arashafsharpour.daresaymovie.features.moviedetail.adapter

import androidx.databinding.ViewDataBinding
import com.arashafsharpour.daresaymovie.BR
import com.arashafsharpour.daresaymovie.R
import com.arashafsharpour.daresaymovie.infrastructure.models.contracts.Posters
import com.arashafsharpour.daresaymovie.infrastructure.platform.BaseSimpleListAdapter
import javax.inject.Inject

class PostersAdapter @Inject() constructor() :
    BaseSimpleListAdapter<Posters, ViewDataBinding>(Posters.DIFF_CALLBACK) {

    override fun getItemViewType(position: Int): Int = R.layout.item_poster

    override fun getItemVariableId(): Int = BR.poster

    override fun onBindViewHolder(holder: DataBindingViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}