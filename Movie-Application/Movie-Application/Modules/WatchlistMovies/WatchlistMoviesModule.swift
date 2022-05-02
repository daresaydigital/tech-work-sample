//
//  WatchlistMoviesModule.swift
//  WatchlistMovies
//
//  Created by Mohanna Zakizadeh on 4/29/22.
//

import UIKit

// MARK: - module builder

final class WatchlistMoviesModule: ModuleInterface {

    typealias View = WatchlistMoviesView
    typealias Presenter = WatchlistMoviesPresenter
    typealias Router = WatchlistMoviesRouter
    typealias Interactor = WatchlistMoviesInteractor

    func build() -> UIViewController {
        guard let navigationController = UIStoryboard(name: "WatchlistMovies", bundle: nil).instantiateInitialViewController() as? UINavigationController else {
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
