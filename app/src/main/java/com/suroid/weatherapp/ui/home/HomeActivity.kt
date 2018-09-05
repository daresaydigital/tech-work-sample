package com.suroid.weatherapp.ui.home

import android.Manifest
import android.animation.AnimatorSet
import android.annotation.SuppressLint
import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.google.android.gms.location.LocationServices
import com.suroid.weatherapp.R
import com.suroid.weatherapp.models.City
import com.suroid.weatherapp.ui.cityselection.CitySelectionActivity
import com.suroid.weatherapp.utils.*
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var viewModel: HomeViewModel

    private lateinit var adapter: HomeViewPagerAdapter

    private var animationSet = AnimatorSet()

    private val fusedLocationClient by lazy {
        LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        window.statusBarColor = ContextCompat.getColor(this, R.color.black_alpha_40)

        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        setupViewPager()
        registerViewListeners()
        registerViewModelObservers()

        setupProgressAnimation(animationSet, progress_bar)
    }

    private fun setupViewPager() {
        adapter = HomeViewPagerAdapter(supportFragmentManager)
        view_pager.adapter = adapter
        view_pager.offscreenPageLimit = 2
        view_pager.pageMargin = (-resources.displayMetrics.widthPixels * 0.25).toInt()
        view_pager.setPageTransformer(false, CoverTransformer(0.2f))
    }

    /**
     * Registering observers for related liveData from viewModel
     */
    private fun registerViewModelObservers() {
        viewModel.cityWeatherListLiveData.observe(this, Observer { cityList ->
            cityList?.let {
                if (it.isNotEmpty()) {
                    group_welcome.visibility = View.GONE
                    adapter.updateList(it)
                } else {
                    group_welcome.visibility = View.VISIBLE
                }
            }
        })

        viewModel.loading.observe(this, Observer { visibility ->
            visibility?.let {
                if (it) {
                    group_welcome.visibility = View.GONE
                    progress_bar.visibility = View.VISIBLE
                    animationSet.start()
                } else {
                    animationSet.cancel()
                    group_welcome.visibility = View.VISIBLE
                    progress_bar.visibility = View.GONE
                }
            }
        })
    }

    /**
     * Registering related listeners for views
     */
    private fun registerViewListeners() {
        fab.setOnClickListener {
            val intent = Intent(this@HomeActivity, CitySelectionActivity::class.java)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this@HomeActivity, fab, ViewCompat.getTransitionName(fab)
                    ?: "")
            ActivityCompat.startActivityForResult(this@HomeActivity, intent, 0, options.toBundle())
        }

        iv_welcome.setOnClickListener {
            requestLocation()
        }

        tv_welcome.setOnClickListener {
            requestLocation()
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestLocation() {
        if (checkAndAskPermissions(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    viewModel.fetchForCurrentLocation(location = it)
                } ?: run { showToast(R.string.cannot_find_location) }
            }.addOnFailureListener {
                showToast(R.string.please_check_gps)
            }
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        handlePermissionResult(Manifest.permission.ACCESS_COARSE_LOCATION) {
            requestLocation()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            data?.getParcelableExtra<City>(CITY_MODEL)?.let {
                viewModel.saveNewCity(it)
            }
        }
    }
}


