//
//  MainTabBarController.swift
//  MovieApplicationMVVM
//
//  Created by Mohanna Zakizadeh on 5/4/22.
//

import UIKit

final class MainTabBarController: UITabBarController {

    // MARK: - Properties
    let topRatedTabBarItem = UITabBarItem(title: TabBarPage.topRated.pageTitleValue(),
                                          image: TabBarPage.topRated.pageIcon(),
                                          selectedImage: TabBarPage.topRated.pageSelectedIcon())
    let popularTabBarItem = UITabBarItem(title: TabBarPage.popular.pageTitleValue(),
                                         image: TabBarPage.popular.pageIcon(),
                                         selectedImage: TabBarPage.popular.pageSelectedIcon())
    let watchlistTabBarItem = UITabBarItem(title: TabBarPage.watchlist.pageTitleValue(),
                                           image: TabBarPage.watchlist.pageIcon(),
                                           selectedImage: TabBarPage.watchlist.pageSelectedIcon())

    var topRatedMovies: TopRatedMoviesCoordinator?
    var popularMovies: PopularMoviesCoordinator?
    var watchlistMovies: WatchlistMoviesCoordinator?

    let parentCoordinator: Coordinator

    init(parentCoordinator: Coordinator) {
        self.parentCoordinator = parentCoordinator
        super.init(nibName: nil, bundle: nil)
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override func viewDidLoad() {
        super.viewDidLoad()

        self.viewControllers = configureViewControllers()
        self.selectedIndex = 1
    }

    private func configureViewControllers() -> [UIViewController] {
        topRatedMovies = TopRatedMoviesCoordinator(tabBarItem: topRatedTabBarItem,
                                                       parentCoordinator: parentCoordinator)
        popularMovies = PopularMoviesCoordinator(tabBarItem: popularTabBarItem,
                                                     parentCoordinator: parentCoordinator)
        watchlistMovies = WatchlistMoviesCoordinator(tabBarItem: watchlistTabBarItem,
                                                         parentCoordinator: parentCoordinator)

        return [popularMovies!.navigationController!,
                topRatedMovies!.navigationController!,
                watchlistMovies!.navigationController!]
    }

}
