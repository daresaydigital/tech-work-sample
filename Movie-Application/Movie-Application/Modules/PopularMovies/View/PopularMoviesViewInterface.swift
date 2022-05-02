//
//  PopularMoviesViewInterface.swift
//  PopularMovies
//
//  Created by Mohanna Zakizadeh on 4/23/22.
//

import UIKit

protocol PopularMoviesViewInterface: ViewPresenterInterface {
    func showError(with error: RequestError)
    func reloadCollectionView()
    func scrollToTop()
}
