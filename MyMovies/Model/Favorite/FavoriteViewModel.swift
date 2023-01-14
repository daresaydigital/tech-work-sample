//
//  FavoriteViewModel.swift
//  MyMovies
//
//  Created by Caio dos Santos Ambrosio on 1/14/23.
//

import Foundation

class FavoriteViewModel {

    // MARK: - Properties

    private let favoriteRepository: FavoriteRepository
    private var favorites: [TrendingViewModel] = []

    var numberOfRows: Int {
        self.favorites.count
    }

    // MARK: - Initializer

    init(favoriteRepository: FavoriteRepository) {
        self.favoriteRepository = favoriteRepository
    }

    // MARK: - Functions

    func fetchFavorites(completion: @escaping (FavoriteViewModel?) -> ()) {
        DispatchQueue.global().async {
            self.favorites.removeAll()
            self.favorites = self.favoriteRepository.getAllFavorites()

            DispatchQueue.main.async {
                completion(self)
            }
        }
    }
}
