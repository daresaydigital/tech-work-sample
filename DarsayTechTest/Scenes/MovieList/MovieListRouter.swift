//
//  MovieListRouter.swift
//  DarsayTechTest
//
//  Created by Farzaneh on 11/8/1401 AP.

import UIKit

protocol MovieListRouterProtocol: AnyObject {
    func routeToDetail(movieID: Int)
    func showErrorAlert(message: String)
    func routeToFavoriteList()
}

class MovieListRouter: MovieListRouterProtocol {
    
    weak var viewController: MovieListViewController?
    
    func routeToDetail(movieID: Int) {
        let destination = MovieDetailBuilder.build(with: .init(movieID: movieID, movieNetworkAPIManager: MovieNetworkAPIManager.shared))
        
        self.viewController?.navigationController?.pushViewController(destination, animated: true)
    }
    
    func showErrorAlert(message: String) {
        let alertController = UIAlertController(title: LocalizeHelper.shared.lookup(.errorTitle),
                                    message: message,
                                    preferredStyle: .alert)
        
        alertController.addAction(UIAlertAction(title: LocalizeHelper.shared.lookup(.ok), style: .default, handler: nil))
        self.viewController?.present(alertController, animated: true)
    }
    
    func routeToFavoriteList() {
        let destination = FavoriteListBuilder.build(with: .init())
        
        self.viewController?.navigationController?.pushViewController(destination, animated: true)
    }
}
