//
//  PopularMoviesModule.swift
//  PopularMovies
//
//  Created by Mohanna Zakizadeh on 4/23/22.
//

import UIKit

// MARK: - module builder

final class PopularMoviesModule: ModuleInterface {

    typealias View = PopularMoviesView
    typealias Presenter = PopularMoviesPresenter
    typealias Router = PopularMoviesRouter
    typealias Interactor = PopularMoviesInteractor

    func build() -> UIViewController {
        guard let navigationController = UIStoryboard(name: "PopularMovies",
                                                      bundle: nil).instantiateInitialViewController()
                as? UINavigationController else {
            return UINavigationController()
        }
        guard let view = navigationController.topViewController as? View else {
            return View()
        }

        let interactor = Interactor()
        let presenter = Presenter()
        let router = Router()

        self.assemble(view: view, presenter: presenter, router: router, interactor: interactor)

        router.viewController = navigationController

        return navigationController
    }
}
