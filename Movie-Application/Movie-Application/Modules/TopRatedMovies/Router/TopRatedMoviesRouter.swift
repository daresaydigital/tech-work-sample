//
//  TopRatedMoviesRouter.swift
//  TopRatedMovies
//
//  Created by Mohanna Zakizadeh on 4/23/22.
//

import UIKit

final class TopRatedMoviesRouter: RouterInterface {

    weak var presenter: TopRatedMoviesPresenterRouterInterface!

    weak var viewController: UIViewController?
}

extension TopRatedMoviesRouter: TopRatedMoviesRouterInterface {

    func showMovieDetails(_ movie: MovieDetail) {
        let movieDetailsViewController = MovieDetailsModule().build(movie: movie)
        viewController?.show(movieDetailsViewController, sender: nil)
    }
}
