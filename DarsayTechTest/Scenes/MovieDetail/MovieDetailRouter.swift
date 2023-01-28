//
//  MovieDetailRouter.swift
//  DarsayTechTest
//
//  Created by Farzaneh on 11/8/1401 AP.

import UIKit

protocol MovieDetailRouterProtocol: AnyObject {
    func showErrorAlert(message: String)
}

final class MovieDetailRouter: MovieDetailRouterProtocol {
    
    weak var viewController: MovieDetailViewController?
    
    func showErrorAlert(message: String) {
        let alertController = UIAlertController(title: "Error",
                                    message: message,
                                    preferredStyle: .alert)
        
        alertController.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
        self.viewController?.present(alertController, animated: true)
    }
}
