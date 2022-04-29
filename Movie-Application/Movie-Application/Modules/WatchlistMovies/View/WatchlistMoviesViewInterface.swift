//
//  WatchlistMoviesViewInterface.swift
//  WatchlistMovies
//
//  Created by mohannazakizadeh on 4/29/22.
//

import UIKit

protocol WatchlistMoviesViewInterface: ViewPresenterInterface {
    func reloadCollectionView()
    func scrollToTop()
}
