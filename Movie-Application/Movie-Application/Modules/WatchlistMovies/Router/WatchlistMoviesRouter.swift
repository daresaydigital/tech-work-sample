//
//  WatchlistMoviesRouter.swift
//  WatchlistMovies
//
//  Created by Mohanna Zakizadeh on 4/29/22.
//

import UIKit

final class WatchlistMoviesRouter: RouterInterface {

    weak var presenter: WatchlistMoviesPresenterRouterInterface!

    weak var viewController: UIViewController?
}

extension WatchlistMoviesRouter: WatchlistMoviesRouterInterface {

    func showMovieDetails(_ movie: MovieDetail) {
        let movieDetailsViewController = MovieDetailsModule().build(movie: movie)
        viewController?.show(movieDetailsViewController, sender: nil)
    }

    // posted notification to switch to popular tab
    func showPopularMovies() {
        NotificationCenter.default.post(name: TabBarViewContorller.selectedTabNotification,
                                        object: nil,
                                        userInfo: ["selectedTab": 0])
    }
}
