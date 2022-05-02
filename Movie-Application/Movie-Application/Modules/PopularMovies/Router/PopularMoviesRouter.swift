//
//  PopularMoviesRouter.swift
//  PopularMovies
//
//  Created by Mohanna Zakizadeh on 4/23/22.
//

import UIKit

final class PopularMoviesRouter: RouterInterface {
    
    weak var presenter: PopularMoviesPresenterRouterInterface!
    
    weak var viewController: UIViewController?
}

extension PopularMoviesRouter: PopularMoviesRouterInterface {
    
    func showMovieDetails(_ movie: MovieDetail) {
        let vc = MovieDetailsModule().build(movie: movie)
        viewController?.show(vc, sender: nil)
    }
    
}
