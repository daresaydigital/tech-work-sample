package com.baryshev.dmitriy.daresayweather.utils.rx

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RxScheduler : IRxScheduler {
    override fun io() = Schedulers.io()

    override fun main() = AndroidSchedulers.mainThread()

    override fun newThread() = Schedulers.newThread()

    override fun computation() = Schedulers.computation()
}