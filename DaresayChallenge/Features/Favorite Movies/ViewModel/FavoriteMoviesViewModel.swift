//
//  FavoriteMoviesViewModel.swift
//  DaresayChallenge
//
//  Created by Keihan Kamangar on 2022-06-29.
//

import Foundation

final class FavoriteMoviesViewModel {
    
    // MARK: - Variables
    var totalCount: Int = 0
    var isFinished: Bool = false
    
    var itemsCount: Int {
        favoriteMovies.count
    }
    
    public var favoriteMovies: [MoviesModel]
    
    // MARK: - Init
    init(favoriteMovies: [MoviesModel] = UserDefaultsData.favoriteList) {
        self.favoriteMovies = favoriteMovies
    }
}

// MARK: - ListViewModelable
extension FavoriteMoviesViewModel: ListViewModelable {
    func item(at index: Int) -> MoviesModel {
        favoriteMovies[index]
    }
    
    func isLoadingCell(for indexPath: IndexPath) -> Bool { false }
    
    func prefetchData() { }
}
