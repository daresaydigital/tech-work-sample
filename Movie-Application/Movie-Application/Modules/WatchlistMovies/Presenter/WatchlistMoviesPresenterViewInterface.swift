//
//  WatchlistMoviesPresenterViewInterface.swift
//  WatchlistMovies
//
//  Created by mohannazakizadeh on 4/29/22.
//

import Foundation
import UIKit

protocol WatchlistMoviesPresenterViewInterface: PresenterViewInterface {
    func viewDidLoad()
    func getMovieImage(index: Int) -> UIImage
    func getMovieTitle(index: Int) -> String
    func showMovieDetails(_ index: Int)
    func deletefromWatchList(_ index: Int)
    func getWatchlistMovies()
    func deleteMovies()
    
    var watchlistMovies: [CoreDataMovie] { get }
    var numberOfMovies: Int { get }
}
