//
//  TopRatedMoviesPresenter.swift
//  TopRatedMovies
//
//  Created by mohannazakizadeh on 4/23/22.
//

import Foundation

final class TopRatedMoviesPresenter: PresenterInterface {

    var router: TopRatedMoviesRouterInterface!
    var interactor: TopRatedMoviesInteractorInterface!
    weak var view: TopRatedMoviesViewInterface!

}

extension TopRatedMoviesPresenter: TopRatedMoviesPresenterRouterInterface {

}

extension TopRatedMoviesPresenter: TopRatedMoviesPresenterInteractorInterface {

}

extension TopRatedMoviesPresenter: TopRatedMoviesPresenterViewInterface {

    func viewDidLoad() {

    }

}
