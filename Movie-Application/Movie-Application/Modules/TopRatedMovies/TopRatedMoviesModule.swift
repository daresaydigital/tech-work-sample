//
//  TopRatedMoviesModule.swift
//  TopRatedMovies
//
//  Created by mohannazakizadeh on 4/23/22.
//

import UIKit

// MARK: - module builder

final class TopRatedMoviesModule: ModuleInterface {

    typealias View = TopRatedMoviesView
    typealias Presenter = TopRatedMoviesPresenter
    typealias Router = TopRatedMoviesRouter
    typealias Interactor = TopRatedMoviesInteractor

    func build() -> UIViewController {
        let view = View()
        let navigator = UINavigationController(rootViewController: view)
        
        let interactor = Interactor()
        let presenter = Presenter()
        let router = Router()

        self.assemble(view: view, presenter: presenter, router: router, interactor: interactor)

        router.viewController = navigator

        return navigator
    }
}
