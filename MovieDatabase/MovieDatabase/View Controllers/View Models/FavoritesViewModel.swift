//
//  FavoritesViewModel.swift
//  MovieDatabase
//
//  Created by Cem Atilgan on 2020-09-16.
//  Copyright © 2020 Cem Atilgan. All rights reserved.
//

import Foundation

protocol FavoritesViewModelDelegate: AnyObject {
    func fetchMovies()
}

final class FavoritesViewModel {
    private weak var delegate: FavoritesViewModelDelegate?

    private var favoriteMovies: [Movie] = []

    var totalNumberOfFavoriteMovies: Int {
        return favoriteMovies.count
    }

    init(delegate: FavoritesViewModelDelegate) {
        self.delegate = delegate
        self.favoriteMovies = UserDefaults.standard.movies
        NotificationCenter.default.addObserver(self, selector: #selector(favoritesChanged), name: .FavoritesChangedNotification, object: nil)
    }

    @objc private func favoritesChanged() {
        fetchTopRatedMovies()
    }

    func favoriteMovie(at index: Int) -> Movie {
        return favoriteMovies[index]
    }

    func fetchTopRatedMovies() {
        self.favoriteMovies = UserDefaults.standard.movies
        delegate?.fetchMovies()
    }
}
