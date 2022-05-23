package ir.sass.home.ui.home

import ir.sass.base_ui.MotherFragment
import ir.sass.base_ui.MotherFragmentSetting
import ir.sass.home.ui.R
import ir.sass.home.ui.databinding.FragmentHomeBinding
import ir.sass.navigator.flow.features.movie.MovieNavigationAction
import ir.sass.navigator.navigator.navigateToFlow
import ir.sass.shared_domain.MovieListType

class HomeFragment : MotherFragment<FragmentHomeBinding>(
    MotherFragmentSetting(R.layout.fragment_home, "Home")
) {
    override fun binding() {
        dataBinding.navigateToPopular = {
            navigateToFlow(getParentNavControllerHelper(), MovieNavigationAction(MovieListType.POPULAR.ordinal))
        }
        dataBinding.navigateToTopRated = {
            navigateToFlow(getParentNavControllerHelper(), MovieNavigationAction(MovieListType.TOP_RATED.ordinal))
        }
        dataBinding.navigateToLocal = {
            navigateToFlow(getParentNavControllerHelper(), MovieNavigationAction(MovieListType.FAVORITE.ordinal))
        }
    }
}