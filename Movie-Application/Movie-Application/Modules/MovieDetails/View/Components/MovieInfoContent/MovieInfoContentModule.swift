//
//  MovieInfoContentModule.swift
//  MovieInfoContent
//
//  Created by Mohanna Zakizadeh on 4/29/22.
//

import UIKit

// MARK: - module builder

final class MovieInfoContentModule: ModuleInterface {

    typealias View = MovieInfoContentView
    typealias Presenter = MovieInfoContentPresenter
    typealias Router = MovieInfoContentRouter
    typealias Interactor = MovieInfoContentInteractor

    func build(movie: MovieDetail) -> UIViewController {
        let view = View()
        let interactor = Interactor()
        let presenter = Presenter()
        let router = Router()
        view.movie = movie

        self.assemble(view: view, presenter: presenter, router: router, interactor: interactor)

        router.viewController = view

        return view
    }
}
