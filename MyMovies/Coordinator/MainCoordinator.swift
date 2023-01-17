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

    func eventOccurred(with type: Event, parameters params: Any?) {
        switch type {
            case .movieClicked:
                guard let movieId = params as? Int64 else { return }

                let api = MovieAPI()
                let apiLoader = APILoader(apiHandler: api)
                let viewModel = MovieViewModel(apiLoader: apiLoader, movieId: movieId)
                let viewController = MovieViewController(movieViewModel: viewModel)

                navigationController.pushViewController(viewController, animated: true)

            case .favoriteClicked:
                let repository = FavoriteRepository()
                let viewModel = FavoriteViewModel(favoriteRepository: repository)
                let viewController = MovieListViewController(favoriteViewModel: viewModel, coordinator: self)

                navigationController.pushViewController(viewController, animated: true)
        }
    }

    func start() {
        let apiTrending = TrendingAPI()
        let apiLoaderTrending = APILoader(apiHandler: apiTrending)
        let trendingViewModel = TrendingListViewModel(apiLoader: apiLoaderTrending)
        let trendingViewController = MovieListViewController(
            trendingListViewModel: trendingViewModel,
            coordinator: self
        )

        let apiTopRated = TopRatedAPI()
        let apiLoaderTopRated = APILoader(apiHandler: apiTopRated)
        let topRatedListViewModel = TopRatedListViewModel(apiLoader: apiLoaderTopRated)
        let topRatedViewController = MovieListViewController(
            topRatedListViewModel: topRatedListViewModel,
            coordinator: self
        )

        let viewController = HomeViewController(
            trendingViewController: trendingViewController,
            topRatedViewController: topRatedViewController,
            coordinator: self
        )

        navigationController.setViewControllers([viewController], animated: false)
    }
}
