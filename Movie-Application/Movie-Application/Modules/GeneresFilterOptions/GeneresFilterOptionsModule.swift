//
//  GeneresFilterOptionsModule.swift
//  GeneresFilterOptions
//
//  Created by mohannazakizadeh on 4/26/22.
//

import UIKit

// MARK: - module builder

final class GeneresFilterOptionsModule: ModuleInterface {

    typealias View = GeneresFilterOptionsView
    typealias Presenter = GeneresFilterOptionsPresenter
    typealias Router = GeneresFilterOptionsRouter
    typealias Interactor = GeneresFilterOptionsInteractor

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
