//
//  FavoriteMoviesModule.swift
//  FavoriteMovies
//
//  Created by mohannazakizadeh on 4/23/22.
//

import UIKit

// MARK: - module builder

final class FavoriteMoviesModule: ModuleInterface {

    typealias View = FavoriteMoviesView
    typealias Presenter = FavoriteMoviesPresenter
    typealias Router = FavoriteMoviesRouter
    typealias Interactor = FavoriteMoviesInteractor

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
