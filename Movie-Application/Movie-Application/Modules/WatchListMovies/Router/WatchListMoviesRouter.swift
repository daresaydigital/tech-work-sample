//
//  WatchListMoviesRouter.swift
//  WatchListMovies
//
//  Created by mohannazakizadeh on 4/28/22.
//

import UIKit

final class WatchListMoviesRouter: RouterInterface {

    weak var presenter: WatchListMoviesPresenterRouterInterface!

    weak var viewController: UIViewController?
}

extension WatchListMoviesRouter: WatchListMoviesRouterInterface {

}
