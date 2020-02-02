package com.sneha.weatherapp.ui.dummies

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.sneha.weatherapp.data.model.Dummy
import com.sneha.weatherapp.ui.base.BaseItemViewModel
import com.sneha.weatherapp.utils.common.Resource
import com.sneha.weatherapp.utils.log.Logger
import com.sneha.weatherapp.utils.network.NetworkHelper
import com.sneha.weatherapp.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class DummyItemViewModel @Inject constructor(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper
) : BaseItemViewModel<Dummy>(schedulerProvider, compositeDisposable, networkHelper) {

    companion object {
        const val TAG = "DummyItemViewModel"
    }

    val name: LiveData<String> = Transformations.map(data) { it.name }
    val url: LiveData<String?> = Transformations.map(data) { it.imageUrl }

    fun onItemClick(position: Int) {
        messageString.postValue(Resource.success("onItemClick at $position of ${data.value?.name}"))
        Logger.d(TAG, "onItemClick at $position")
    }

    override fun onCreate() {
        Logger.d(TAG, "onCreate called")
    }
}