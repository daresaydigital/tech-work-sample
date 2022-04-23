//
//  TabBarModule.swift
//  TabBar
//
//  Created by mohannazakizadeh on 4/23/22.
//

import UIKit

// MARK: - module builder

final class TabBarModule: ModuleInterface {

    typealias View = TabBarView
    typealias Presenter = TabBarPresenter
    typealias Router = TabBarRouter
    typealias Interactor = TabBarInteractor

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
