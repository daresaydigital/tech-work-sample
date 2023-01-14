//
//  UINavigationControllerMock.swift
//  MyMoviesTests
//
//  Created by Caio dos Santos Ambrosio on 1/14/23.
//

import UIKit
@testable import MyMovies

final class MainCoordinatorMock: Coordinator {
    var startCalled = false
    var eventOccurredCalled = false

    var navigationController: UINavigationController

    init(navigationController: UINavigationController) {
        self.navigationController = navigationController
    }

    func eventOccurred(with type: Event, parameters params: Any?) {
        self.eventOccurredCalled = true
    }

    func start() {
        self.startCalled = true
    }
}
