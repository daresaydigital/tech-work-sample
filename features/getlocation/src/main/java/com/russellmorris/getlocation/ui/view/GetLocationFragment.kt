package com.russellmorris.getlocation.ui.view

import android.content.Context
import android.content.Intent
import android.location.Location
import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.russellmorris.getlocation.R
import com.russellmorris.getlocation.data.provider.LocationProvider
import com.russellmorris.getlocation.data.provider.LocationProviderImpl
import com.russellmorris.getlocation.data.provider.LocationResultListener
import com.russellmorris.getlocation.injectFeature
import com.russellmorris.getlocation.ui.viewmodel.GetLocationViewModel
import com.russellmorris.presentation.base.BaseFragment
import com.russellmorris.presentation.base.BaseViewModel
import kotlinx.android.synthetic.main.get_location_fragment.*
import org.koin.androidx.viewmodel.ext.viewModel

class GetLocationFragment : Fragment(), LocationResultListener {

    lateinit var locationProvider: LocationProvider

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.get_location_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injectFeature()
        locationPermissionButton.setOnClickListener{v -> activity?.let {
            locationProvider.getLocation(it)
        } }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        locationProvider = LocationProviderImpl(requireContext(), this)
        locationProvider.initialiseLocationClient()
    }

    override fun locationPermissionPreviouslyDeniedWithNeverAskAgain() {
        val settingsIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val settingUri = Uri.fromParts("package", activity?.packageName, null)
        settingsIntent.setData(settingUri)
        startActivity(settingsIntent)
    }

    override fun setLocation(location: Location?) {
        // Launch weather details fragment
        println("Location: $location")
    }

}
