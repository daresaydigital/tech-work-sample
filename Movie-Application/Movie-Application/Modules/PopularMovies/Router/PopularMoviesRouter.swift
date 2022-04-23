//
//  PopularMoviesRouter.swift
//  PopularMovies
//
//  Created by mohannazakizadeh on 4/23/22.
//

import UIKit

final class PopularMoviesRouter: RouterInterface {

    weak var presenter: PopularMoviesPresenterRouterInterface!

    weak var viewController: UIViewController?
}

extension PopularMoviesRouter: PopularMoviesRouterInterface {

}
