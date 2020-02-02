package com.sneha.weatherapp.di.module

import androidx.lifecycle.LifecycleRegistry
import com.sneha.weatherapp.di.ViewModelScope
import com.sneha.weatherapp.ui.base.BaseItemViewHolder
import dagger.Module
import dagger.Provides

@Module
class ViewHolderModule(private val viewHolder: BaseItemViewHolder<*, *>) {

    @Provides
    @ViewModelScope
    fun provideLifecycleRegistry(): LifecycleRegistry = LifecycleRegistry(viewHolder)
}