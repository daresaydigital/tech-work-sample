//
//  WatchlistMoviesRouterInterface.swift
//  WatchlistMovies
//
//  Created by Mohanna Zakizadeh on 4/29/22.
//

import UIKit

protocol WatchlistMoviesRouterInterface: RouterPresenterInterface {
    func showMovieDetails(_ movie: MovieDetail)
    func showPopularMovies()
}
