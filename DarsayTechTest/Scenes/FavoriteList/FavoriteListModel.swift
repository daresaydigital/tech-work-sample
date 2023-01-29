//
//  FavoriteListModel.swift
//  DarsayTechTest
//
//  Created by Farzaneh on 11/9/1401 AP.

import Foundation

enum FavoriteList {
	
    struct Configuration {
    
    }
    
    struct State: StateProtocol {
        var favoriteList: [Movie]?
    }
    
    enum Action {
        case fetchFavoriteMovies
    }
}
