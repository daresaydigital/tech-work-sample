//
//  TabBarPresenter.swift
//  TabBar
//
//  Created by mohannazakizadeh on 4/23/22.
//

import Foundation

final class TabBarPresenter: PresenterInterface {

    var router: TabBarRouterInterface!
    var interactor: TabBarInteractorInterface!
    weak var view: TabBarViewInterface!

}

extension TabBarPresenter: TabBarPresenterRouterInterface {

}

extension TabBarPresenter: TabBarPresenterInteractorInterface {

}

extension TabBarPresenter: TabBarPresenterViewInterface {

    func viewDidLoad() {

    }

    func topRatedMoviesTabDidTap() {
        
    }
    
    func popularMoviesTabDidTap() {
        
    }
    
    func favoriteMoviesTabDidTap() {
        
    }
    
}
