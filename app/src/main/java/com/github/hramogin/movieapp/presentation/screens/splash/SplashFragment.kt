package com.github.hramogin.movieapp.presentation.screens.splash

import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.hramogin.movieapp.R
import com.github.hramogin.movieapp.databinding.FragmentSplashBinding
import com.github.hramogin.movieapp.navigation.AppNavigator
import com.github.hramogin.movieapp.presentation.base.BaseFragment
import com.github.hramogin.movieapp.presentation.base.extensions.observeEvent
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : BaseFragment<SplashViewModel, FragmentSplashBinding>() {

    override val viewModel: SplashViewModel by viewModel()

    override fun getLayoutId(): Int = R.layout.fragment_splash

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSplashBinding =
        { inflater, container, attached ->
            val contextThemeWrapper =
                ContextThemeWrapper(activity, R.style.AppTheme_MovieApp_Splash)
            val localInflater = inflater.cloneInContext(contextThemeWrapper)
            FragmentSplashBinding.inflate(localInflater, container, attached)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.openMainScreenAction.observeEvent(viewLifecycleOwner) {
            AppNavigator.toMainScreen(this)
        }
    }
}