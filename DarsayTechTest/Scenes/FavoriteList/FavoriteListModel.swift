//
//  FavoriteListModel.swift
//  DarsayTechTest
//
//  Created by Farzaneh on 11/9/1401 AP.
//  Copyright (c) 1401 AP ___ORGANIZATIONNAME___. All rights reserved.
//
//  This file was generated based on the Clean Swift and MVVM Architecture
//

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
