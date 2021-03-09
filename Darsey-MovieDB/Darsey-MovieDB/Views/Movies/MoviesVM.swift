//
//  MoviesViewModel.swift
//  Darsey-MovieDB
//
//  Created by Emil Vaklinov on 09/03/2021.
//

import Foundation

struct MoviesViewModel {

    private let movie: Movie
    
    init(_ movie: Movie) {
        self.movie = movie
    }
    
    var title: String {
        return self.movie.title
    }
    
    var release_date: String {
        return self.movie.release_date
    }
    
    var vote_average: Double {
        return self.movie.vote_average
    }
    
    var poster_path: String? {
        return self.movie.poster_path
    }
    
    var backdrop_path: String? {
        return self.movie.backdrop_path
    }
    
    var id: Int {
        return self.movie.id
    }

    var posterURL: String {
        guard let poster_path = movie.poster_path else { return "" }
        return ApiURL.image + "w342/\(poster_path)"
    }
    
    var backdropURL: String {
            guard let backdrop_path = movie.backdrop_path else { return "" }
            return ApiURL.image + "original/\(backdrop_path)"
        }
}
