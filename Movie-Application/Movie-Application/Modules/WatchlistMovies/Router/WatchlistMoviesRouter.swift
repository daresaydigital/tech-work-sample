//
//  WatchlistMoviesRouter.swift
//  WatchlistMovies
//
//  Created by mohannazakizadeh on 4/29/22.
//

import UIKit

final class WatchlistMoviesRouter: RouterInterface {

    weak var presenter: WatchlistMoviesPresenterRouterInterface!

    weak var viewController: UIViewController?
}

extension WatchlistMoviesRouter: WatchlistMoviesRouterInterface {
    func showMovieDetails(id: Int) {
        
    }
}
