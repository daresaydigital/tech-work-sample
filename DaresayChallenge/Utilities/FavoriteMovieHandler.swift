//
//  FavoriteMovieHandler.swift
//  DaresayChallenge
//
//  Created by Keihan Kamangar on 2022-06-29.
//

import Foundation

protocol MovieFavorable: AnyObject {
    func addToFavorites(_ model: MoviesModel)
    func removeFromFavorites(_ model: MoviesModel)
}

final class FavoriteMoviesHandler {
    
    // MARK: - Variables
    static let shared: MovieFavorable = FavoriteMoviesHandler()
}

// MARK: - MovieFavorable
extension FavoriteMoviesHandler: MovieFavorable {
    func addToFavorites(_ model: MoviesModel) {
        if UserDefaultsData.favoriteList.contains(where: {$0 == model}) { return }
        UserDefaultsData.favoriteList.append(model)
    }
    
    func removeFromFavorites(_ model: MoviesModel) {
        var favList = UserDefaultsData.favoriteList
        favList.removeAll(where: {$0 == model})
        UserDefaultsData.favoriteList = favList
    }
}

