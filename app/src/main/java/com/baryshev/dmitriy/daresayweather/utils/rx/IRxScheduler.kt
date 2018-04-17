package com.baryshev.dmitriy.daresayweather.utils.rx

import io.reactivex.Scheduler

interface IRxScheduler {
    fun io():Scheduler
    fun main():Scheduler
    fun newThread():Scheduler
    fun computation():Scheduler
}