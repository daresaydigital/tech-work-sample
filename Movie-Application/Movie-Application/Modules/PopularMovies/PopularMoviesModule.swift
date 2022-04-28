//
//  PopularMoviesModule.swift
//  PopularMovies
//
//  Created by mohannazakizadeh on 4/23/22.
//

import UIKit

// MARK: - module builder

final class PopularMoviesModule: ModuleInterface {

    typealias View = PopularMoviesView
    typealias Presenter = PopularMoviesPresenter
    typealias Router = PopularMoviesRouter
    typealias Interactor = PopularMoviesInteractor

    func build() -> UIViewController {
        let view = View()
        let navigationController = UINavigationController(rootViewController: view)
        
        let interactor = Interactor()
        let presenter = Presenter()
        let router = Router()

        self.assemble(view: view, presenter: presenter, router: router, interactor: interactor)

        router.viewController = navigationController

        return navigationController
    }
}
