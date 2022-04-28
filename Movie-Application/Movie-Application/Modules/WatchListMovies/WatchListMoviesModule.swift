//
//  WatchListMoviesModule.swift
//  WatchListMovies
//
//  Created by mohannazakizadeh on 4/28/22.
//

import UIKit

// MARK: - module builder

final class WatchListMoviesModule: ModuleInterface {

    typealias View = WatchListMoviesView
    typealias Presenter = WatchListMoviesPresenter
    typealias Router = WatchListMoviesRouter
    typealias Interactor = WatchListMoviesInteractor

    func build() -> UIViewController {
        let view = View()
        let interactor = Interactor()
        let presenter = Presenter()
        let router = Router()

        self.assemble(view: view, presenter: presenter, router: router, interactor: interactor)

        router.viewController = view

        return view
    }
}
