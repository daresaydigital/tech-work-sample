package com.vp.weatherapp.ui

import android.support.annotation.CallSuper
import com.vp.weatherapp.util.BasePresenter
import com.vp.weatherapp.util.BaseView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


abstract class AbstractPresenter<V : BaseView<P>, out P : BasePresenter<V>> : BasePresenter<V> {

    override lateinit var view: V

    val disposables = CompositeDisposable()

    fun launch(job: () -> Disposable) {
        disposables.add(job())
    }

    @CallSuper
    override fun stop() {
        disposables.clear()
    }
}