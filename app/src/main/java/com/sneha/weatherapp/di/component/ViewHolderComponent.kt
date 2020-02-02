package com.sneha.weatherapp.di.component

import com.sneha.weatherapp.di.ViewModelScope
import com.sneha.weatherapp.di.module.ViewHolderModule
import com.sneha.weatherapp.ui.dummies.DummyItemViewHolder
import dagger.Component

@ViewModelScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [ViewHolderModule::class]
)
interface ViewHolderComponent {

    fun inject(viewHolder: DummyItemViewHolder)
}