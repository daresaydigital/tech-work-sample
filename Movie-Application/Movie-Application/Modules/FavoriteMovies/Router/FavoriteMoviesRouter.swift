//
//  FavoriteMoviesRouter.swift
//  FavoriteMovies
//
//  Created by mohannazakizadeh on 4/23/22.
//

import UIKit

final class FavoriteMoviesRouter: RouterInterface {

    weak var presenter: FavoriteMoviesPresenterRouterInterface!

    weak var viewController: UIViewController?
}

extension FavoriteMoviesRouter: FavoriteMoviesRouterInterface {

}
