//
//  GeneresFilterOptionsPresenter.swift
//  GeneresFilterOptions
//
//  Created by mohannazakizadeh on 4/26/22.
//

import Foundation

final class GeneresFilterOptionsPresenter: PresenterInterface {

    var router: GeneresFilterOptionsRouterInterface!
    var interactor: GeneresFilterOptionsInteractorInterface!
    weak var view: GeneresFilterOptionsViewInterface!

}

extension GeneresFilterOptionsPresenter: GeneresFilterOptionsPresenterRouterInterface {

}

extension GeneresFilterOptionsPresenter: GeneresFilterOptionsPresenterInteractorInterface {

}

extension GeneresFilterOptionsPresenter: GeneresFilterOptionsPresenterViewInterface {

    func viewDidLoad() {

    }

}
