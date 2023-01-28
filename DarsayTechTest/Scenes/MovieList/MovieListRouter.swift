//
//  MovieListRouter.swift
//  DarsayTechTest
//
//  Created by Farzaneh on 11/8/1401 AP.

import UIKit

protocol MovieListRouterProtocol: AnyObject {
    func routeToDetail(movieID: Int)
    func showErrorAlert(message: String)
}

class MovieListRouter: MovieListRouterProtocol {
    
    weak var viewController: MovieListViewController?
    
    func routeToDetail(movieID: Int) {
        
    }
    
    func showErrorAlert(message: String) {
        let alertController = UIAlertController(title: "Error",
                                    message: message,
                                    preferredStyle: .alert)
        
        alertController.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
        self.viewController?.present(alertController, animated: true)
    }
}
