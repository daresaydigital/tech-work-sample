package com.vp.weatherapp.ui.selection

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.vp.weatherapp.R
import com.vp.weatherapp.data.local.entity.CityEntity
import com.vp.weatherapp.data.local.entity.CityWithForecast
import com.vp.weatherapp.di.Params.SELECTION_VIEW
import kotlinx.android.synthetic.main.activity_selection.*
import org.koin.android.ext.android.inject


class SelectionActivity : AppCompatActivity(), SelectionContract.View {

    override val presenter: SelectionContract.Presenter by inject { mapOf(SELECTION_VIEW to this) }

    private var selectionAdapter = SelectionAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection)
        setupRecycler()
        presenter.getSelectedCities()
    }

    fun setupRecycler() {
        recycler.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL))
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = selectionAdapter

        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = recycler.adapter as SelectionAdapter
                val position = viewHolder.adapterPosition
                val city = adapter.getItemAtPosition(position)
                adapter.removeAt(position)
                presenter.deleteSelectedCity(city)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recycler)
    }

    override fun showSelectedCities(results: List<CityWithForecast>) {
        selectionAdapter = SelectionAdapter(results.toMutableList())
        recycler.swapAdapter(selectionAdapter, true)
    }

    override fun onCityDeleted() {
        presenter.getSelectedCities()
    }

    override fun onStop() {
        presenter.stop()
        super.onStop()
    }

    companion object {

        fun newIntent(context: Context): Intent = Intent(context, SelectionActivity::class.java)
    }
}