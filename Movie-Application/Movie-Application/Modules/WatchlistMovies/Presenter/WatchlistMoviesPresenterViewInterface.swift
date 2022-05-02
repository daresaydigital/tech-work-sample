//
//  WatchlistMoviesPresenterViewInterface.swift
//  WatchlistMovies
//
//  Created by Mohanna Zakizadeh on 4/29/22.
//

import Foundation
import UIKit

protocol WatchlistMoviesPresenterViewInterface: PresenterViewInterface {
    func viewDidLoad()
    func getMovieImage(index: Int) -> UIImage
    func getMovieTitle(index: Int) -> String
    func movieSelected(at index: Int)
    func deletefromWatchList(_ index: Int)
    func getWatchlistMovies()
    func deleteMovies()
    func alertRetryButtonDidTap(_ index: Int)
    func sortByDate()
    func sortByName()
    func sortByUserScore()
    func browseMoviesDidTap()
    
    var watchlistMovies: [CoreDataMovie] { get }
    var numberOfMovies: Int { get }
}
