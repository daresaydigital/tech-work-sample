package com.github.hramogin.movieapp.presentation.base.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<T, out VB: ViewBinding>(binding: VB, protected val listener: (item: T) -> Unit = {}) :
    RecyclerView.ViewHolder(binding.root){

    open fun onBind(item: T?) {
        val data = item ?: return
        itemView.setOnClickListener {
            listener.invoke(data)
        }
    }
}