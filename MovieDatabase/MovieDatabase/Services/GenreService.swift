//
//  GenreService.swift
//  MovieDatabase
//
//  Created by Cem Atilgan on 2020-09-16.
//  Copyright © 2020 Cem Atilgan. All rights reserved.
//

import Foundation

class GenreService {

    static let shared = GenreService()
    private var genres: [Genre] = []
    private var networkService: NetworkService!

    private init() {}

    func fetchGenres() {
        networkService = NetworkService()

        networkService.fetchGenres { (result) in
            switch result {
            case .success(let genreResult):
                self.genres = genreResult.genres
            case .failure(let error):
                print(error.localizedDescription)
            }
        }
    }

    func genre(for movie: Movie) -> Genre? {
        guard let movieGenreId = movie.genreIDs.first else { return nil }

        let genreName = genres.filter { $0.id == movieGenreId }

        return genreName.first
    }
}
