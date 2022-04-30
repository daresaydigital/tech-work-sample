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
    
    func showMovieDetails(_ movie: MovieDetail) {
        let vc = MovieDetailsModule().build(movie: movie)
        viewController?.show(vc, sender: nil)
    }
}
