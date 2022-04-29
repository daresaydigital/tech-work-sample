//
//  MovieDetailsModule.swift
//  MovieDetails
//
//  Created by mohannazakizadeh on 4/29/22.
//

import UIKit

// MARK: - module builder

final class MovieDetailsModule: ModuleInterface {

    typealias View = MovieDetailsView
    typealias Presenter = MovieDetailsPresenter
    typealias Router = MovieDetailsRouter
    typealias Interactor = MovieDetailsInteractor

    func build(movie: MovieDetail) -> UIViewController {
        let movieInfoContentView = MovieInfoContentModule().build(movie: movie) as! MovieInfoContentView
        let movieDetailsInfoViewController = MovieDetailsInfoViewController()
        movieDetailsInfoViewController.movie = movie
        
        let view = View(contentViewController: movieInfoContentView , bottomSheetViewController: movieDetailsInfoViewController, bottomSheetConfiguration: .init(height: UIScreen.main.bounds.height*0.8, initialOffset: UIScreen.main.bounds.height / 2.2))
        
        let navigation = UINavigationController(rootViewController: view)
        
        let interactor = Interactor()
        let presenter = Presenter()
        let router = Router()

        self.assemble(view: view, presenter: presenter, router: router, interactor: interactor)

        router.viewController = navigation

        return navigation
    }
}
