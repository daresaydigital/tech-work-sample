//
//  TopRatedMoviesViewInterface.swift
//  TopRatedMovies
//
//  Created by Mohanna Zakizadeh on 4/23/22.
//

import UIKit

protocol TopRatedMoviesViewInterface: ViewPresenterInterface {
    func showError(with error: RequestError)
    func reloadCollectionView()
    func scrollToTop()
}
