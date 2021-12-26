package com.arashafsharpour.daresaymovie.infrastructure.platform

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.DiffUtil

abstract class BaseSimpleListAdapter<T, DB : ViewDataBinding>(
    diffCallback: DiffUtil.ItemCallback<T>
) : BaseListAdapter<T, BaseSimpleListAdapter<T, DB>.DataBindingViewHolder>(diffCallback) {

    var onItemClicked: ((item: T, position: Int, view: View) -> Unit)? = null
    var onBindItem: ((binding: DB, item: T) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder {
        return DataBindingViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                viewType,
                parent,
                false
            ) as DB
        )
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder, position: Int) =
        holder.bind(getItem(position))

    abstract override fun getItemViewType(position: Int): Int

    abstract fun getItemVariableId(): Int

    inner class DataBindingViewHolder(private val binding: DB) : BaseViewHolder(binding) {

        override val lifecycleRegistry = LifecycleRegistry(this)

        init {
            lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        }

        override fun getLifecycle(): Lifecycle {
            return lifecycleRegistry
        }

        fun bind(item: T) {
            binding.apply {
                lifecycleOwner = this@DataBindingViewHolder
                setVariable(getItemVariableId(), item)
                executePendingBindings()
                onBindItem?.invoke(binding, item)
                root.apply {
                    setOnClickListener {
                        onItemClicked?.invoke(item, bindingAdapterPosition, this)
                    }
                }
            }
        }
    }
}