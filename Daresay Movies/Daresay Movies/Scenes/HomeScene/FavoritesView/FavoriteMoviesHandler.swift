//
//  FavoriteMoviesHandler.swift
//  Daresay Movies
//
//  Created by Emad Bayramy on 12/14/21.
//

import Foundation

protocol MovieFavorable: AnyObject {
    func fave(_ model: MovieModel)
    func unfave(_ model: MovieModel)
}

class FavoriteMoviesHandler {
    
    static let shared: MovieFavorable = FavoriteMoviesHandler()
    
    func fave(_ model: MovieModel) {
        if UserDefaultData.favoriteList.contains(where: {$0 == model}) { return }
        UserDefaultData.favoriteList.append(model)
    }
    
    func unfave(_ model: MovieModel) {
        var favList = UserDefaultData.favoriteList
        favList.removeAll(where: {$0 == model})
        UserDefaultData.favoriteList = favList
    }
}

extension FavoriteMoviesHandler: MovieFavorable { }
