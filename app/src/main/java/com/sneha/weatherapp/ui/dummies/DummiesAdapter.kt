package com.sneha.weatherapp.ui.dummies

import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import com.sneha.weatherapp.data.model.Dummy
import com.sneha.weatherapp.ui.base.BaseAdapter

class DummiesAdapter(
    parentLifecycle: Lifecycle,
    private val dummies: ArrayList<Dummy>
) : BaseAdapter<Dummy, DummyItemViewHolder>(parentLifecycle, dummies) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DummyItemViewHolder(parent)
}