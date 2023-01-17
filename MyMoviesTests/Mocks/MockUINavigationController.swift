//
//  UINavigationControllerMock.swift
//  MyMoviesTests
//
//  Created by Caio dos Santos Ambrosio on 1/14/23.
//

import UIKit
@testable import MyMovies

final class MockUINavigationController: UINavigationController {
    var controllers: [UIViewController] = []

    var setViewControllersCalled = false
    var pushViewControllerCalled = false

    override func setViewControllers(_ viewControllers: [UIViewController], animated: Bool) {
        controllers = viewControllers
        setViewControllersCalled = true
    }

    override func pushViewController(_ viewController: UIViewController, animated: Bool) {
        controllers.insert(viewController, at: 0)
        pushViewControllerCalled = true
    }
}
