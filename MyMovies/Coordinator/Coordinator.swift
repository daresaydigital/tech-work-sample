//
//  Coordinator.swift
//  MyMovies
//
//  Created by Caio dos Santos Ambrosio on 1/10/23.
//

import UIKit

protocol Coordinator {
    var navigationController: UINavigationController { get }

    func eventOccurred(with type: Event, parameters params: Any?)
    func start()
}

protocol Coordinating {
    var coordinator: Coordinator? { get set }
}
