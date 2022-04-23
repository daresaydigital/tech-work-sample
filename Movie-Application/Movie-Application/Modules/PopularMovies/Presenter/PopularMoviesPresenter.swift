//
//  PopularMoviesPresenter.swift
//  PopularMovies
//
//  Created by mohannazakizadeh on 4/23/22.
//

import Foundation

final class PopularMoviesPresenter: PresenterInterface {

    var router: PopularMoviesRouterInterface!
    var interactor: PopularMoviesInteractorInterface!
    weak var view: PopularMoviesViewInterface!

}

extension PopularMoviesPresenter: PopularMoviesPresenterRouterInterface {

}

extension PopularMoviesPresenter: PopularMoviesPresenterInteractorInterface {

}

extension PopularMoviesPresenter: PopularMoviesPresenterViewInterface {

    func viewDidLoad() {

    }

}
