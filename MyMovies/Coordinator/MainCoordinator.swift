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
        let api = TrendingAPI()
        let apiLoader = APILoader(apiHandler: api)
        let trendingViewModel = TrendingListViewModel(apiLoader: apiLoader)
        let trendingViewController = TrendingViewController(trendingListViewModel: trendingViewModel)

        let topRatedViewController = TopRatedViewController()

        let viewController = HomeViewController(
            trendingViewController: trendingViewController,
            topRatedViewController: topRatedViewController
        )

        navigationController.setViewControllers([viewController], animated: false)
    }
}
