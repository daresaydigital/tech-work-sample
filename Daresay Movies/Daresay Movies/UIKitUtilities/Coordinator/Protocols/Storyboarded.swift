//
//  Storyboarded.swift
//  Daresay Movies
//
//  Created by Emad Bayramy on 12/13/21.
//

import UIKit

/*
 
 Insted of setting dependency of ViewController for coordinator, by confirming to Storyboarded protocol we can easily instantiate our ViewControllers
 
 */

protocol Storyboarded {
    associatedtype ConcreteCoordinator
    var coordinator: ConcreteCoordinator? { get set }
    static func instantiate() -> Self
}

extension Storyboarded where Self: UIViewController {
    
    private static var fileName: String {
        NSStringFromClass(self)
    }
 
    private static var className: String {
        fileName.components(separatedBy: ".")[1]
    }

    private static var storyboardName: String {
        className.deletingSuffix("ViewController")
    }
    
    private static var storyboard: UIStoryboard {
        UIStoryboard(name: storyboardName, bundle: Bundle.main)
    }

    static func instantiate() -> Self {
        guard let vc = storyboard.instantiateViewController(withIdentifier: className) as? Self else {
            fatalError("Could not find View Controller named \(className)")
        }
        return vc
    }
}

extension Storyboarded where Self: UIViewController {

    static func instantiate(coordinator: ConcreteCoordinator?) -> Self {
        var viewController = instantiate()
        viewController.coordinator = coordinator
        return viewController
    }
}

fileprivate extension String {

    /// Removes the given String from the end of the string String.
    /// If the text is not present, returns the original String intact.
    ///
    /// - Parameters:
    ///     - suffix: The text to be removed, e.g. "ViewController"
    ///
    /// - Returns:
    ///     - If suffix was found, String with the suffix removed, e.g. "MainViewController" -> "Main"
    ///     - If no suffix was found, the original string intact. e.g. "MainCoordinator" -> "MainCoordinator"
    ///
    func deletingSuffix(_ suffix: String) -> String {
        guard self.hasSuffix(suffix) else { return self }
        return String(self.dropLast(suffix.count))
    }
}
