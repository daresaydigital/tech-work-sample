//
//  MovieInfoContentRouter.swift
//  MovieInfoContent
//
//  Created by mohannazakizadeh on 4/29/22.
//

import UIKit

final class MovieInfoContentRouter: RouterInterface {

    weak var presenter: MovieInfoContentPresenterRouterInterface!

    weak var viewController: UIViewController?
}

extension MovieInfoContentRouter: MovieInfoContentRouterInterface {

}
