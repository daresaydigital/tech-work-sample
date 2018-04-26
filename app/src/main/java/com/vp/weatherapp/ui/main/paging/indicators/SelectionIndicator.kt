package com.vp.weatherapp.ui.main.paging.indicators


interface SelectionIndicator {

    val selectedItemIndex: Int

    var numberOfItems: Int

    var transitionDuration: Int

    val isVisible: Boolean

    fun setSelectedItem(index: Int, animate: Boolean)

    fun setVisibility(show: Boolean)
}