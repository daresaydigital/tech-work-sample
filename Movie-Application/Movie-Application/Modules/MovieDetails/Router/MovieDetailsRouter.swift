//
//  MovieDetailsRouter.swift
//  MovieDetails
//
//  Created by mohannazakizadeh on 4/29/22.
//

import UIKit

final class MovieDetailsRouter: RouterInterface {

    weak var presenter: MovieDetailsPresenterRouterInterface!

    weak var viewController: UIViewController?
}

extension MovieDetailsRouter: MovieDetailsRouterInterface {

}
