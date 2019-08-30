package com.example.weatherapp.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.viewModels.CityAddViewModel
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_city_add.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class CityAddActivity : AppCompatActivity() {



    private val cityAddViewModel: CityAddViewModel by viewModel()
    private lateinit var adapter: CityAddAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_add)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        subscribeUi()
        attachHandlers()

        prepareRecycler()
    }

    private fun prepareRecycler() {
        adapter = CityAddAdapter(this, cityAddViewModel::handleEvent)
        val layoutManager = LinearLayoutManager(this)
        filteredCitiesRecycler.layoutManager = layoutManager
        filteredCitiesRecycler.adapter = adapter
        filteredCitiesRecycler.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))
    }

    private fun subscribeUi() {
        cityAddViewModel.filteredList.observe(this, Observer {
            it?.let { adapter.update(it) }
        })

        cityAddViewModel.selectCity.observe(this, Observer {
            it?.getContentIfNotHandled()?.let {
                // send id as result to weather activity
                val data = Intent().apply {
                    putExtra(CITY_ID_EXTRA, it.id)
                }
                setResult(Activity.RESULT_OK, data)
                finish()
            }
        })
    }

    private fun attachHandlers() {
        clearSearch.clicks().subscribe {
            searchCityEditText.editableText.clear()
            cityAddViewModel.clearResult()
        }
        searchCityEditText.textChanges()
            .debounce(300, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                cityAddViewModel.filter(it.toString())
            }
    }

    companion object {
        val CITY_ID_EXTRA = "city_id"
    }
}
