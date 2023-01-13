//
//  MainCoordinator.swift
//  MyMovies
//
//  Created by Caio dos Santos Ambrosio on 1/10/23.
//

import UIKit

class MainCoordinator: Coordinator {

    let navigationController: UINavigationController

    init(navigationController: UINavigationController) {
        self.navigationController = navigationController
    }

    func start() {
        let apiTrending = TrendingAPI()
        let apiLoaderTrending = APILoader(apiHandler: apiTrending)
        let trendingViewModel = TrendingListViewModel(apiLoader: apiLoaderTrending)
        let trendingViewController = TrendingViewController(trendingListViewModel: trendingViewModel)

        let apiTopRated = TopRatedAPI()
        let apiLoaderTopRated = APILoader(apiHandler: apiTopRated)
        let topRatedListViewModel = TopRatedListViewModel(apiLoader: apiLoaderTopRated)
        let topRatedViewController = TrendingViewController(topRatedListViewModel: topRatedListViewModel)

        let viewController = HomeViewController(
            trendingViewController: trendingViewController,
            topRatedViewController: topRatedViewController
        )

        navigationController.setViewControllers([viewController], animated: false)
    }
}
