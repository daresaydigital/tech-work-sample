package com.baryshev.dmitriy.daresayweather

import com.baryshev.dmitriy.daresayweather.utils.rx.IRxScheduler
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

/**
 * 1/12/2018.
 */
class TestRxScheduler : IRxScheduler {
    override fun io(): Scheduler = Schedulers.trampoline()

    override fun main(): Scheduler = Schedulers.trampoline()

    override fun newThread(): Scheduler = Schedulers.trampoline()

    override fun computation(): Scheduler = Schedulers.trampoline()
}