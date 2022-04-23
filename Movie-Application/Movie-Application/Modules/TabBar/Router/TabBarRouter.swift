//
//  TabBarRouter.swift
//  TabBar
//
//  Created by mohannazakizadeh on 4/23/22.
//

import UIKit

final class TabBarRouter: RouterInterface {

    weak var presenter: TabBarPresenterRouterInterface!

    weak var viewController: UIViewController?
}

extension TabBarRouter: TabBarRouterInterface {

}
