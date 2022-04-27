//
//  MoviesCollectionRouter.swift
//  MoviesCollection
//
//  Created by mohannazakizadeh on 4/26/22.
//

import UIKit

final class MoviesCollectionRouter: RouterInterface {

    weak var presenter: MoviesCollectionPresenterRouterInterface!

    weak var viewController: UIViewController?
}

extension MoviesCollectionRouter: MoviesCollectionRouterInterface {
    func showMovieDetails(id: Int) {
        
    }
}
