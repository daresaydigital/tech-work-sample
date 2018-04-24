package com.vp.weatherapp.ui.selection

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.WindowManager
import com.vp.weatherapp.R
import com.vp.weatherapp.data.local.entity.CityWithForecast
import com.vp.weatherapp.di.Params.SELECTION_VIEW
import kotlinx.android.synthetic.main.activity_selection.*
import org.koin.android.ext.android.inject


class SelectionActivity : AppCompatActivity(), SelectionContract.View {

    override val presenter: SelectionContract.Presenter by inject { mapOf(SELECTION_VIEW to this) }

    private lateinit var selectionAdapter: SelectionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection)
        setupStatusBar()
        setupRecycler()
    }

    private fun setupStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    private fun setupRecycler() {
        val dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider)
        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        dividerItemDecoration.setDrawable(dividerDrawable!!)
        recycler.addItemDecoration(dividerItemDecoration)

        recycler.layoutManager = LinearLayoutManager(this)
        selectionAdapter = SelectionAdapter(this, mutableListOf())
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

    override fun onResume() {
        super.onResume()
        presenter.getSelectedCities()
    }


    override fun showSelectedCities(results: List<CityWithForecast>) {
        selectionAdapter = SelectionAdapter(this, results.toMutableList())
        selectionAdapter.items = results.toMutableList()
        selectionAdapter.notifyDataSetChanged()
        selectionAdapter.notifyItemRangeChanged(0, results.size)
        recycler.swapAdapter(selectionAdapter, true)
    }

    override fun onCityDeleted() {
        presenter.getSelectedCities()
    }

    override fun onPause() {
        presenter.stop()
        super.onPause()
    }


    companion object {

        fun newIntent(context: Context): Intent = Intent(context, SelectionActivity::class.java)
    }
}