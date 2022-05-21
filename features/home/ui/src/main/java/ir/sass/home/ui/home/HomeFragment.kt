package ir.sass.home.ui.home

import androidx.navigation.fragment.findNavController
import ir.sass.base_ui.MotherFragment
import ir.sass.base_ui.MotherFragmentSetting
import ir.sass.home.ui.R
import ir.sass.home.ui.databinding.FragmentHomeBinding
import ir.sass.navigator.flow.features.movie.MovieNavigationAction
import ir.sass.navigator.navigator.navigateToFlow

class HomeFragment : MotherFragment<FragmentHomeBinding>(
    MotherFragmentSetting(R.layout.fragment_home,"Home")
) {
    override fun binding() {
        dataBinding.navigate = {
            navigateToFlow(getParentNavController(), MovieNavigationAction())
        }
    }
}