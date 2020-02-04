package com.sneha.weatherapp.ui.dummies

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.sneha.weatherapp.R
import com.sneha.weatherapp.di.component.FragmentComponent
import com.sneha.weatherapp.ui.base.BaseFragment
import javax.inject.Inject

class DummiesFragment : BaseFragment<DummiesViewModel>() {

    companion object {

        const val TAG = "DummiesFragment"

        fun newInstance(): DummiesFragment {
            val args = Bundle()
            val fragment = DummiesFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var dummiesAdapter: DummiesAdapter

    override fun provideLayoutId(): Int = R.layout.fragment_dummies

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun setupObservers() {
//        viewModel.getWeatherData().observe(this, Observer {
//            Log.e(TAG, "${it?.main?.temperature}")
////            it?.run { dummiesAdapter.appendData(this) }
//        })
    }

    override fun setupView(view: View) {
//        rv_dummy.layoutManager = linearLayoutManager
//        rv_dummy.adapter = dummiesAdapter
    }
}