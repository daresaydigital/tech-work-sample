package ir.sass.ui.home

import androidx.navigation.fragment.findNavController
import ir.sass.base_ui.MotherFragment
import ir.sass.base_ui.MotherFragmentSetting
import ir.sass.navigator.flow.NavigationAction
import ir.sass.navigator.flow.features.movie.MovieNavigationAction
import ir.sass.navigator.navigator.navigateToFlow
import ir.sass.ui.R
import ir.sass.ui.databinding.FragmentHomeBinding

class HomeFragment : MotherFragment<FragmentHomeBinding>(
    MotherFragmentSetting(R.layout.fragment_home,"Home")
) {
    override fun binding() {
        dataBinding.navigate = {
            navigateToFlow(findNavController(), MovieNavigationAction())
        }
    }
}