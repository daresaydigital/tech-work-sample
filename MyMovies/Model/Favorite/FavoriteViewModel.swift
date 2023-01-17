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

    var numberOfRowsInSection: Int {
        self.favorites.count
    }

    let titlePage: String = "Favorites"

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

    func getTrending(_ index: Int) -> TrendingViewModel {
        return self.favorites[index]
    }

    func removeFavorite(for movieId: Int64) {
        self.favorites.removeAll(where: { $0.movieId == movieId })
        favoriteRepository.deleteMovie(for: movieId)
    }
}
