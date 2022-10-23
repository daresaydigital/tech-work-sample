package com.github.hramogin.movieapp.presentation.base.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<T, VB : ViewBinding>(
    protected var items: List<T> = emptyList(),
    protected val listener: (item: T) -> Unit = {}
) :
    RecyclerView.Adapter<BaseViewHolder<T, VB>>() {

    override fun onBindViewHolder(holder: BaseViewHolder<T, VB>, position: Int) {
        if (items.isEmpty()) {
            holder.onBind(null)
        } else {
            holder.onBind(items[position])
        }
    }

    override fun getItemCount(): Int = if (items.isEmpty()) 1 else items.size

    override fun getItemViewType(position: Int): Int {
        return if (items.isEmpty()) EMPTY_TYPE else GENERAL_TYPE
    }

    open fun setNewData(newItems: List<T>) {
        items = newItems
        notifyDataSetChanged()
    }

    companion object {
        const val EMPTY_TYPE = 0
        const val GENERAL_TYPE = 1
    }
}