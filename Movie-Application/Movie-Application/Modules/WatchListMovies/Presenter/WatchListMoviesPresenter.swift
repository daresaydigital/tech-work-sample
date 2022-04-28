//
//  WatchListMoviesPresenter.swift
//  WatchListMovies
//
//  Created by mohannazakizadeh on 4/28/22.
//

import Foundation

final class WatchListMoviesPresenter: PresenterInterface {

    var router: WatchListMoviesRouterInterface!
    var interactor: WatchListMoviesInteractorInterface!
    weak var view: WatchListMoviesViewInterface!

}

extension WatchListMoviesPresenter: WatchListMoviesPresenterRouterInterface {

}

extension WatchListMoviesPresenter: WatchListMoviesPresenterInteractorInterface {

}

extension WatchListMoviesPresenter: WatchListMoviesPresenterViewInterface {

    func viewDidLoad() {

    }

}
