//
//  FavoriteListRouter.swift
//  DarsayTechTest
//
//  Created by Farzaneh on 11/9/1401 AP.
//  Copyright (c) 1401 AP ___ORGANIZATIONNAME___. All rights reserved.
//
//  This file was generated based on the Clean Swift and MVVM Architecture
//

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
