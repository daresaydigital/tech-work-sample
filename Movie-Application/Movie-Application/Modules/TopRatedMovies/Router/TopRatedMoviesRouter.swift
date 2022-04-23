//
//  TopRatedMoviesRouter.swift
//  TopRatedMovies
//
//  Created by mohannazakizadeh on 4/23/22.
//

import UIKit

final class TopRatedMoviesRouter: RouterInterface {

    weak var presenter: TopRatedMoviesPresenterRouterInterface!

    weak var viewController: UIViewController?
}

extension TopRatedMoviesRouter: TopRatedMoviesRouterInterface {

}
