//
//  WatchlistMoviesViewInterface.swift
//  WatchlistMovies
//
//  Created by Mohanna Zakizadeh on 4/29/22.
//

import UIKit

protocol WatchlistMoviesViewInterface: ViewPresenterInterface {
    func reloadCollectionView()
    func scrollToTop()
    func showError(with error: RequestError, index: Int)
    func setWatchlistEmptyContainerisHidden(to isHidden: Bool)
}
