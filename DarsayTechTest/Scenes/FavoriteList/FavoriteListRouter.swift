//
//  FavoriteListRouter.swift
//  DarsayTechTest
//
//  Created by Farzaneh on 11/9/1401 AP.

import UIKit

protocol FavoriteListRouterProtocol: AnyObject {
    func routeToDetail(movieID: Int)
}

class FavoriteListRouter: FavoriteListRouterProtocol {
	
	weak var viewController: FavoriteListViewController?
    
    func routeToDetail(movieID: Int) {
        let destination = MovieDetailBuilder.build(with: .init(movieID: movieID, movieNetworkAPIManager: MovieNetworkAPIManager.shared))
        
        self.viewController?.navigationController?.pushViewController(destination, animated: true)
    }
}
