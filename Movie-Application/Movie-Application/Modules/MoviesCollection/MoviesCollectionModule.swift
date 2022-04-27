//
//  MoviesCollectionModule.swift
//  MoviesCollection
//
//  Created by mohannazakizadeh on 4/26/22.
//

import UIKit

// MARK: - module builder

final class MoviesCollectionModule: ModuleInterface {

    typealias View = MoviesCollectionView
    typealias Presenter = MoviesCollectionPresenter
    typealias Router = MoviesCollectionRouter
    typealias Interactor = MoviesCollectionInteractor

    func build(movies: [Movie], viewType: ViewType) -> UIViewController {
        let view = View()
        let interactor = Interactor()
        let presenter = Presenter(movies: movies)
        let router = Router()
        view.viewType = viewType
        
        self.assemble(view: view, presenter: presenter, router: router, interactor: interactor)

        router.viewController = view

        return view
    }
}
