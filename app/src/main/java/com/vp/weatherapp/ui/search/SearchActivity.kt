package com.vp.weatherapp.ui.search

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.WindowManager
import com.jakewharton.rxbinding2.widget.RxTextView
import com.vp.weatherapp.R
import com.vp.weatherapp.data.local.entity.CityEntity
import com.vp.weatherapp.di.Params.SEARCH_VIEW
import com.vp.weatherapp.ui.main.MainActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.search_bar.*
import kotlinx.android.synthetic.main.search_results.*
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit


class SearchActivity : AppCompatActivity(), SearchContract.View {

    private val formatCityCountry by lazy { getString(R.string.format_city_country) }

    private val listener = { city: CityEntity ->
        presenter.saveSelectedCity(city)
    }

    override val presenter: SearchContract.Presenter by inject { mapOf(SEARCH_VIEW to this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.location_search)
        setupSearchBox()
        setupListView()
        setupStatusBar()
        close_button_wrapper.setOnClickListener { finish() }
    }

    private fun setupSearchBox() {
        RxTextView.textChanges(location_search_box)
                .filter { it.length > 0 }
                .debounce(400, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ text -> presenter.performSearch(text.toString()) })
    }

    private fun setupListView() {
        location_search_result.layoutManager = LinearLayoutManager(this)
        location_search_result.adapter = SearchAdapter(emptyList(), formatCityCountry, listener)
    }

    private fun setupStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    override fun searchComplete(results: List<CityEntity>) {
        Log.e("SearchActivity", "searchComplete ${results.size}" )
        if (results.isEmpty()) {
            empty.visibility = View.VISIBLE
            location_search_result.visibility = View.GONE
        } else {
            empty.visibility = View.GONE
            location_search_result.visibility = View.VISIBLE
        }
        location_search_result.swapAdapter(SearchAdapter(results, formatCityCountry, listener), false)
    }

    override fun onCitySelected(success: Boolean) {
        if (success) {
            startActivity(MainActivity.newIntent(this))
            finish()
        }
    }

    override fun onDestroy() {
        presenter.stop()
        super.onDestroy()
    }

    companion object {

        fun newIntent(context: Context): Intent = Intent(context, SearchActivity::class.java)
    }


}