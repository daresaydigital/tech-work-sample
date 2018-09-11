package com.suroid.weatherapp.ui.cityselection

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.transition.Transition
import com.suroid.weatherapp.R
import com.suroid.weatherapp.models.City
import com.suroid.weatherapp.utils.*
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_city_selection.*
import javax.inject.Inject

class CitySelectionActivity : AppCompatActivity(), CitySelectionAdapter.CityAdapterDelegate, HasSupportFragmentInjector {


    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: CitySelectionViewModel
    private lateinit var adapter : CitySelectionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_selection)

        adapter = CitySelectionAdapter(this, this)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CitySelectionViewModel::class.java)

        setupEnterAnimation()

        registerViewModelObservers()

        registerViewListeners()

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapter
    }

    /**
     * Registering observers for related liveData from viewModel
     */
    private fun registerViewModelObservers() {
        viewModel.cityListLiveData.observe(this, Observer { cityList ->
            cityList?.let {
                adapter.updateCityList(it)
            }
        })

        viewModel.queryText.observe(this, Observer { query ->
            query?.let {
                et_search.setText(it)
            }
        })
    }

    /**
     * Registering related listeners for views
     */
    private fun registerViewListeners() {
        iv_back.setOnClickListener {
            performExit()
        }

        iv_cancel.setOnClickListener {
            viewModel.refreshData()
        }

        et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.searchForCities(s.toString())
            }

        })
    }

    /**
     * This method sets up enter animation and makes the root view visible after reveal animation
     */
    private fun setupEnterAnimation() {
        window.sharedElementEnterTransition.addListener(object : Transition.TransitionListener {
            override fun onTransitionEnd(p0: Transition?) {
                val cx = (activity_root.left + activity_root.right) / 2
                val cy = (activity_root.top + activity_root.bottom) / 2
                animateRevealShow(activity_root, fab.width / 2, R.color.bg_color, cx,
                        cy, object : OnRevealAnimationListener {
                    override fun onRevealShow() {
                        fab.hide()
                        root_layout.animate().alpha(1f).duration = 300
                        et_search.isFocusableInTouchMode = true
                        et_search.requestFocus()
                        showKeyboard(this@CitySelectionActivity, et_search)
                    }

                    override fun onRevealHide() {
                    }
                })
            }

            override fun onTransitionResume(p0: Transition?) {
            }

            override fun onTransitionPause(p0: Transition?) {
            }

            override fun onTransitionCancel(p0: Transition?) {
            }

            override fun onTransitionStart(p0: Transition?) {
            }

        })
    }

    override fun onItemClick(city: City) {
        setResult(Activity.RESULT_OK, Intent().apply {
            putExtra(CITY_MODEL, city)
        })
        performExit()
    }

    override fun onBackPressed() {
        performExit()
    }

    /**
     * Performs exit after reveal Animation
     */
    private fun performExit() {
        fab.show()
        hideKeyboard(this, et_search)
        animateRevealHide(activity_root, R.color.bg_color, fab.width / 2,
                object : OnRevealAnimationListener {

                    override fun onRevealShow() {
                    }

                    override fun onRevealHide() {
                        root_layout.alpha = 0f
                        ActivityCompat.finishAfterTransition(this@CitySelectionActivity)

                    }
                })
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector
}
