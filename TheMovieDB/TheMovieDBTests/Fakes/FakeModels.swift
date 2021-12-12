//
//  FakeModels.swift
//  TheMovieDBTests
//
//  Created by Ali Sani on 12/11/21.
//

import Foundation
@testable import TheMovieDB

final class FakeModels {

    static var FakeMovieDetails: MovieDetails = {
        return MovieDetails(id: 1,
                            genres: [],
                            overview: nil,
                            title: "title",
                            posterPath: nil,
                            backdropPath: nil)
    }()
}
